package guru.springframework.trainingmsscbeerservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;
import guru.springframework.trainingmsscbeerservice.web.controller.NotFoundException;
import guru.springframework.trainingmsscbeerservice.web.mapper.BeerMapper;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	@Override
	public BeerDto getById(UUID beerId) {
		
		return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
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
