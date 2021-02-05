package guru.springframework.trainingmsscbeerservice.service.brewing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.trainingmsscbeerservice.config.JmsConfig;
import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.events.BrewBeerEvent;
import guru.springframework.trainingmsscbeerservice.events.NewInventoryEvent;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

	private final BeerRepository beerRepository;
	private final JmsTemplate jmsTemplate;
	
	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
	public void listen(BrewBeerEvent event) {
		
		BeerDto beerDto = event.getBeerDto();
		
		Beer beer = beerRepository.getOne(beerDto.getId());
		
		beerDto.setQuantityOnHand(beer.getQuantityToBrew());
		
		NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
		
		log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand()); 
		
		jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
	}
}
