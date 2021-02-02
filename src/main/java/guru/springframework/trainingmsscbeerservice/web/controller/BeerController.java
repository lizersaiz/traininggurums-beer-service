package guru.springframework.trainingmsscbeerservice.web.controller;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.trainingmsscbeerservice.service.BeerService;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import guru.springframework.trainingmsscbeerservice.web.model.BeerPagedList;
import guru.springframework.trainingmsscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//had to change route due to assignment requirements and extend it on all old endpoints
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	
	private final BeerService beerService;
	
	// DELEGATE EXTERNAL REQUEST TO LISTBEERS TO TRANSFORM RESPONSE BY DECORATING IT LATER ON
	@GetMapping(produces = {"application/json"}, path = "beer")
	public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
													@RequestParam(value = "pageSize", required = false) Integer pageSize,
													@RequestParam(value = "beerName", required = false) String beerName,
													@RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
													@RequestParam(value = "isQuantityOnHand", required = false) Boolean isQuantityOnHand) {
		
		if (pageNumber == null || pageNumber <0) {
			
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		
		if (pageSize == null || pageSize <0) {
			
			pageSize = DEFAULT_PAGE_SIZE;
		}
		
		if (isQuantityOnHand == null) {
			
			isQuantityOnHand = false;
		}
		
		BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), isQuantityOnHand);
		
		return new ResponseEntity<>(beerList, HttpStatus.OK);
	}
	
	@GetMapping("beerUpc/{upc}")
	public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc,
												@RequestParam(value = "isQuantityOnHand", required = false) Boolean isQuantityOnHand) {
		
		//System.out.println("cache hitting up");
		
		if (isQuantityOnHand == null) {
			isQuantityOnHand = false;
		}
		
		return new ResponseEntity<BeerDto>(beerService.getByUpc(upc, isQuantityOnHand), HttpStatus.OK);
	}
	
	@GetMapping("beer/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId, 
												@RequestParam(value = "isQuantityOnHand", required = false) Boolean isQuantityOnHand) {
		
		if (isQuantityOnHand == null) {
			
			isQuantityOnHand = false;
		}
		
		return new ResponseEntity<>(beerService.getById(beerId, isQuantityOnHand), HttpStatus.OK);
	}
	
	@PostMapping(path = "beer")
	public ResponseEntity<BeerDto> createNewBeer(@RequestBody @Validated BeerDto beerDto) {

		return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
	}
	
	@PutMapping("beer/{beerId}")
	public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {

		return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
	}
}
