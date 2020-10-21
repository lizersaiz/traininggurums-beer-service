package guru.springframework.trainingmsscbeerservice.service.inventory;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.trainingmsscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

	private final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
	private final RestTemplate restTemplate;
	
	private String beerInventoryServiceHost;

	// INJECT PROPERTY FROM FILE
	public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
		
		this.beerInventoryServiceHost = beerInventoryServiceHost;
	}
	
	// BUILD RESTTEMPLATE
	public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
		
		this.restTemplate = restTemplateBuilder.build();
	}
	
	// METHOD THAT WILL SEND A REQUEST TO INVENTORY SERVICE APP AND WILL HANDLE RESPONSE INFORMATION
	@Override
	public Integer getOnHandInventory(UUID beerId) {

		log.debug("Calling inventory service");
		
		// NOTE EXCHANGE METHOD WILL SEND THE REQUEST, PLACING THE PATH PARAM INTO INVENTORY_PATH
		ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate
				.exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
						new ParameterizedTypeReference<List<BeerInventoryDto>>() {}, (Object) beerId);
		
		// HANDLE THE DATA
		Integer onHand = Objects.requireNonNull(responseEntity.getBody())
				.stream()
				.mapToInt(BeerInventoryDto::getQuantityOnHand)
				.sum();
		
		return onHand;
	}
}
