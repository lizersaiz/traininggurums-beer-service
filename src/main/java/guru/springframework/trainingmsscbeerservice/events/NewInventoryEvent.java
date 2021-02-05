package guru.springframework.trainingmsscbeerservice.events;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

//JACKSON REQUIRES A NOARGSCONSTRUCTOR
@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {
	
	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
