package guru.springframework.trainingmsscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;

// CommandLineRunner contains the run method which allows to execute code to Bootstrap the application
// multiple CommandLineRunner classes can be run on the same app
public class BeerLoader implements CommandLineRunner {

	private final BeerRepository beerRepository;	
	
	public BeerLoader(BeerRepository beerRepository) {

		this.beerRepository = beerRepository;
	}
	

	@Override
	public void run(String... args) throws Exception {

		if (beerRepository.count() == 0) {
			
			beerRepository.save(Beer.builder()
					.beerName("Mango Bobs")
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(337010000001L)
					.price(new BigDecimal("12.95"))
					.build());
			
			beerRepository.save(Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(337010000002L)
					.price(new BigDecimal("11.95"))
					.build());
		}
	}

}
