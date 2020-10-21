package guru.springframework.trainingmsscbeerservice.web.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

@DecoratedWith(BeerMapperDecorator.class)
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

	BeerDto beerToBeerDto (Beer beer);
	
	BeerDto beerToBeerDtoWithInventory (Beer beer);
	
	Beer beerDtoToBeer (BeerDto beerDto);
}
