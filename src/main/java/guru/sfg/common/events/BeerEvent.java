package guru.sfg.common.events;

import java.io.Serializable;

import guru.sfg.trainingmsscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
// JACKSON REQUIRES A NOARGSCONSTRUCTOR
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable{

	private static final long serialVersionUID = 5284614446490788523L;

	private BeerDto beerDto;
}
