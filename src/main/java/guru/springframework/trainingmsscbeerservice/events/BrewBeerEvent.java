package guru.springframework.trainingmsscbeerservice.events;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

//JACKSON REQUIRES A NOARGSCONSTRUCTOR
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent{

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
