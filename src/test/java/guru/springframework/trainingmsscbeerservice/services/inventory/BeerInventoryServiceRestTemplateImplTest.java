package guru.springframework.trainingmsscbeerservice.services.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.trainingmsscbeerservice.bootstrap.BeerLoader;
import guru.springframework.trainingmsscbeerservice.service.inventory.BeerInventoryService;

@Disabled //utility for manual testing
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {

	@Autowired
	BeerInventoryService beerInventoryService;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void getOnHandInventory() {
		
		Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
		System.out.println(qoh);
	}
}
