package guru.springframework.trainingmsscbeerservice.events;

import java.io.Serializable;

import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable{

	private static final long serialVersionUID = 5284614446490788523L;

	private final BeerDto beerDto;
}
