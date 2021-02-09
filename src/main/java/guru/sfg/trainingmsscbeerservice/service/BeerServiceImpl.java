package guru.sfg.trainingmsscbeerservice.service;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.sfg.trainingmsscbeerservice.domain.Beer;
import guru.sfg.trainingmsscbeerservice.repository.BeerRepository;
import guru.sfg.trainingmsscbeerservice.web.controller.NotFoundException;
import guru.sfg.trainingmsscbeerservice.web.mapper.BeerMapper;
import guru.sfg.trainingmsscbeerservice.web.model.BeerDto;
import guru.sfg.trainingmsscbeerservice.web.model.BeerPagedList;
import guru.sfg.trainingmsscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	//			corresponds to cache config alias on ehcache.xml
	//								will only work on a method parameter condition in this case
	@Cacheable(cacheNames = "beerListCache", condition = "#isQuantityOnHand == false")
	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean isQuantityOnHand) {

		System.out.println("I was called, not again until cache has expired");
		
		// REPOSITORY WILL RETURN A SINGLE PAGE WITH ALL DATA ON IT
		Page<Beer> beerPage;
		// THAT PAGE WILL BE TRANSFORMED ONTO A PAGEDLIST BASED ON PAGEREQUEST
		BeerPagedList beerPagedList;
		
		if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			
			beerPage = beerRepository.findAll(pageRequest);
		}
		
		if (isQuantityOnHand) {
			
			beerPagedList = new BeerPagedList(beerPage
				.getContent()
				.stream()
				// CALL NEW MAPPER METHOD
				.map(beerMapper::beerToBeerDtoWithInventory)
				.collect(Collectors.toList()),
				PageRequest
					.of(beerPage.getPageable().getPageNumber(),
							beerPage.getPageable().getPageSize()),
				beerPage.getTotalElements());
		} else {
			
			beerPagedList = new BeerPagedList(beerPage
				.getContent()
				.stream()
				// HERE DECORATOR WILL COME INTO PLACE
				.map(beerMapper::beerToBeerDto)
				.collect(Collectors.toList()),
				PageRequest
					.of(beerPage.getPageable().getPageNumber(),
							beerPage.getPageable().getPageSize()),
				beerPage.getTotalElements());
		}
		
		// CONVERSION PAGE - PAGEDLIST
	
		
		return beerPagedList;
	}
	
	//key parameter tells to cache the different results given by the method according to that parameter
	//by default, keys will be automatically generated for the requested keys on parameters
	@Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#isQuantityOnHand == false")
	@Override
	public BeerDto getById(UUID beerId, Boolean isQuantityOnHand) {


		if (isQuantityOnHand) {

			return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
		}
		
		return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));		
	}


	@Cacheable(cacheNames = "beerCacheUpc", condition = "#isQuantityOnHand == false")
	@Override
	public BeerDto getByUpc(String upc, Boolean isQuantityOnHand) {

		if (isQuantityOnHand) {
			
			return beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc));
		}
		
		return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
	}
	
	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {

		return beerMapper.beerToBeerDto(
			beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
		);
	}

	@Override
	public BeerDto updateBeerById(UUID beerId, BeerDto beerDto) {
		
		Beer beerToUpdate = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		beerToUpdate.setBeerName(beerDto.getBeerName());
		beerToUpdate.setBeerStyle(beerDto.getBeerStyle().name());
		beerToUpdate.setPrice(beerDto.getPrice());
		beerToUpdate.setUpc(beerDto.getUpc());
		
		return beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate));
	}
}
