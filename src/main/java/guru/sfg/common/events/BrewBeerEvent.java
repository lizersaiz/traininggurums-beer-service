package guru.sfg.common.events;

import guru.sfg.trainingmsscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

//JACKSON REQUIRES A NOARGSCONSTRUCTOR
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent{

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
