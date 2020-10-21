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
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	
	private final BeerService beerService;
	
	// DELEGATE EXTERNAL REQUEST TO LISTBEERS TO TRANSFORM RESPONSE BY DECORATING IT LATER ON
	@GetMapping(/* produces = {"application/json"} */)
	public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
													@RequestParam(value = "pageSize", required = false) Integer pageSize,
													@RequestParam(value = "beerName", required = false) String beerName,
													@RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle) {
		
		if (pageNumber == null || pageNumber <0) {
			
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		
		if (pageSize == null || pageSize <0) {
			
			pageSize = DEFAULT_PAGE_SIZE;
		}
		
		BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
		
		return new ResponseEntity<>(beerList, HttpStatus.OK);
	}
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId) {

		return new ResponseEntity<>(beerService.getById(beerId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<BeerDto> createNewBeer(@RequestBody @Validated BeerDto beerDto) {

		return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{beerId}")
	public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {

		return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
	}
}
