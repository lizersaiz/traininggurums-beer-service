package guru.springframework.trainingmsscbeerservice.web.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
// METHODS WILL BE CALLED NORMALLY BY FIRST INJECTING A BEERMAPPER INTERFACE
// BUT BEERMAPPERDECORATOR IMPLEMENTATION WILL TAKE PRIORITY IF OVERRIDES ANY
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

	BeerDto beerToBeerDto (Beer beer);
	
	Beer beerDtoToBeer (BeerDto beerDto);
}
