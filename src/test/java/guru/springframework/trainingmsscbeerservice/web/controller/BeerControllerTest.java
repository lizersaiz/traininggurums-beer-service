package guru.springframework.trainingmsscbeerservice.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

//These wont work with RestDocs, need to use the one below
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

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
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.repository.BeerRepository;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import guru.springframework.trainingmsscbeerservice.web.model.BeerStyleEnum;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
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
		
		//need to specify path parameters, not just append in the url request
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
			//param just for testing query params, controller will just ignore it as it is not asking for it
			.param("iscold", "yes")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("v1/beer", 
				pathParameters(
						parameterWithName("beerId").description("UUID of desired beer to get.")
				),
				requestParameters(
						parameterWithName("iscold").description("Is Beer Cold Query param.")
				),
				//If payload fields are documented, the test will throw an error if there is one missing
				responseFields(
						fieldWithPath("id").description("Id of beer"),
						fieldWithPath("version").description("Version number"),
						fieldWithPath("createdDate").description("Date Created"),
						fieldWithPath("lastModifiedDate").description("Date Updated"),
						fieldWithPath("beerName").description("Beer Name"),
						fieldWithPath("beerStyle").description("Beer Style"),
						fieldWithPath("upc").description("UPC of Beer"),
						fieldWithPath("price").description("Price"),
						fieldWithPath("quantityOnHand").description("Quantity on Hand")	
				)));
	}

	@Test
	void createNewBeer() throws Exception {

		//given
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated())
				.andDo(document("v1/beer",
					requestFields(
						//use ignored to prevent test to fail when missing property
						//to use constraint documentation, a package 
						//tree must be created (test.resources.org.springframework.restdocs.templates)
						//it must contain a "request-fields.snippet" template
						fields.withPath("id").ignored(),
						fields.withPath("version").ignored(),
						fields.withPath("createdDate").ignored(),
						fields.withPath("lastModifiedDate").ignored(),
						fields.withPath("beerName").description("Beer Name"),
						fields.withPath("beerStyle").description("Beer Style"),
						fields.withPath("upc").description("UPC of Beer").attributes(),
						fields.withPath("price").description("Price"),
						fields.withPath("quantityOnHand").ignored()
					)));
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
	
	//custom class for constraint documentation. It will
	//1 Contain a ConstraintDescription object which will give access to a class constraint description
	//2 Constructor will accept any class and it will instantiate a Constraint descrptions object with that class
	//3 withPath method will return an enhanced "fieldWithPath" method, adding the constraints from a given path (the property)
	private static class ConstrainedFields {
		
		private final ConstraintDescriptions constraintDescriptions;
		
		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}
		
		private FieldDescriptor withPath(String path) {
			return fieldWithPath(path).attributes(key("constraints")
					.value(StringUtils.collectionToDelimitedString(
							this.constraintDescriptions.descriptionsForProperty(path), ". ")));
		}
	}

}
