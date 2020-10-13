package guru.springframework.trainingmsscbeerservice.service;

import java.util.UUID;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

public interface BeerService {

	BeerDto getById(UUID beerId);

	BeerDto saveNewBeer(BeerDto beerDto);

	BeerDto updateBeerById(UUID beerId, BeerDto beerDto);

}
