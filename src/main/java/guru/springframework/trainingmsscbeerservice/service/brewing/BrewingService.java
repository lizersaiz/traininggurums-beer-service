package guru.springframework.trainingmsscbeerservice.service.brewing;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import guru.springframework.trainingmsscbeerservice.config.JmsConfig;
import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.events.BrewBeerEvent;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;
import guru.springframework.trainingmsscbeerservice.service.inventory.BeerInventoryService;
import guru.springframework.trainingmsscbeerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

	private final BeerRepository beerRepository;
	private final BeerInventoryService beerInventoryService;
	private final JmsTemplate jmsTemplate;
	private final BeerMapper beerMapper;
	
	@Scheduled(fixedRate = 5000)
	public void checkForLowInventory() {
		
		List<Beer> beers = beerRepository.findAll();
		
		beers.forEach(beer -> {
			
			Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());
			
			log.debug("Min Onhand is: " + beer.getMinOnHand());
			log.debug("Inventory is:" + invQOH);
			
			if (beer.getMinOnHand() >= invQOH) {
				
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});
	}
}
