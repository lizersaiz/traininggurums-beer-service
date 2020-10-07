package guru.springframework.trainingmsscbeerservice.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import guru.springframework.trainingmsscbeerservice.web.model.BeerStyleEnum;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
//TODO actually needed? (added into sfgrestdocsexample project)
//@ComponentScan(basePackages = "guru.springframework.trainingmsscbeerservice.web.mappers")
@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	BeerRepository beerRepository;
	
	@Test
	void getBeerById() throws Exception {
		
		given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));
		
		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void createNewBeer() throws Exception {

		//given
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated());
	}

	@Test
	void updateBeerById() throws Exception {

		//given
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDtoJson))
			.andExpect(status().isNoContent());
	}
	
	private BeerDto getValidBeerDto() {
		
		return BeerDto.builder().beerName("beer name")
				.beerStyle(BeerStyleEnum.ALE)
				.upc(123456789L)
				.price(new BigDecimal("12.95"))
				.build();
	}

}
