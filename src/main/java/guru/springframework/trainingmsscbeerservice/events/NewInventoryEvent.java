package guru.springframework.trainingmsscbeerservice.events;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {
	
	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
