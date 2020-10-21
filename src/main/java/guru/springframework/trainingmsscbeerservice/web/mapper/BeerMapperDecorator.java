package guru.springframework.trainingmsscbeerservice.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.service.inventory.BeerInventoryService;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

public abstract class BeerMapperDecorator implements BeerMapper {

	private BeerInventoryService beerInventoryService;
	private BeerMapper beerMapper;
	
	@Autowired
	public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
		
		this.beerInventoryService = beerInventoryService;
	}
	
	@Autowired
	public void setMapper(BeerMapper mapper) { this.beerMapper = mapper; }
	
	// NOT ENHANCED BEERDTO
	@Override
	public BeerDto beerToBeerDto(Beer beer) {
		
		BeerDto dto = beerMapper.beerToBeerDto(beer);
		System.out.println(dto);
		return dto;
	}
	
	// WANT TO RETURN AN ENHANCED BEERDTO WITH EXTRA INFORMATION THAT WILL COME FROM INVENTORY SERVICE APP
	@Override
	public BeerDto beerToBeerDtoWithInventory(Beer beer) {
		
		BeerDto dto = beerMapper.beerToBeerDto(beer);
		dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
		System.out.println(dto);
		return dto;
	}
	
	@Override
	public Beer beerDtoToBeer(BeerDto beerDto) { return beerMapper.beerDtoToBeer(beerDto); }
}
