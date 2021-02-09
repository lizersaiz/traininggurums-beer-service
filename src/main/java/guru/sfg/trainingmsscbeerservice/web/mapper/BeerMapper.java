package guru.sfg.trainingmsscbeerservice.web.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import guru.sfg.trainingmsscbeerservice.domain.Beer;
import guru.sfg.trainingmsscbeerservice.web.model.BeerDto;

@DecoratedWith(BeerMapperDecorator.class)
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

	BeerDto beerToBeerDto (Beer beer);
	
	BeerDto beerToBeerDtoWithInventory (Beer beer);
	
	Beer beerDtoToBeer (BeerDto beerDto);
}
