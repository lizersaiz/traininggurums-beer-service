package guru.springframework.trainingmsscbeerservice.events;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent{

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
