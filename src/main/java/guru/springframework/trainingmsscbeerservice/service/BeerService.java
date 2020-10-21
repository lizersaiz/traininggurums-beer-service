package guru.springframework.trainingmsscbeerservice.service;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import guru.springframework.trainingmsscbeerservice.web.model.BeerPagedList;
import guru.springframework.trainingmsscbeerservice.web.model.BeerStyleEnum;

public interface BeerService {

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean isQuantityOnHand);
	
	BeerDto getById(UUID beerId, Boolean isQuantityOnHand);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeerById(UUID beerId, BeerDto beerDto);
}
