package guru.springframework.trainingmsscbeerservice.web.mapper;

import guru.springframework.trainingmsscbeerservice.domain.Beer;
import guru.springframework.trainingmsscbeerservice.domain.Beer.BeerBuilder;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto;
import guru.springframework.trainingmsscbeerservice.web.model.BeerDto.BeerDtoBuilder;
import guru.springframework.trainingmsscbeerservice.web.model.BeerStyleEnum;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-15T02:31:14+0200",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 1.3.1100.v20200828-0941, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.beerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.beerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.id( beer.getId() );
        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.price( beer.getPrice() );
        beerDto.upc( beer.getUpc() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }

        return beerDto.build();
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        if ( beerDto == null ) {
            return null;
        }

        BeerBuilder beer = Beer.builder();

        beer.beerName( beerDto.getBeerName() );
        if ( beerDto.getBeerStyle() != null ) {
            beer.beerStyle( beerDto.getBeerStyle().name() );
        }
        beer.createdDate( dateMapper.asTimestamp( beerDto.getCreatedDate() ) );
        beer.id( beerDto.getId() );
        beer.lastModifiedDate( dateMapper.asTimestamp( beerDto.getLastModifiedDate() ) );
        beer.price( beerDto.getPrice() );
        beer.upc( beerDto.getUpc() );
        if ( beerDto.getVersion() != null ) {
            beer.version( beerDto.getVersion().longValue() );
        }

        return beer.build();
    }
}
