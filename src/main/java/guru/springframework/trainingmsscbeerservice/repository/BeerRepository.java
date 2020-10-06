package guru.springframework.trainingmsscbeerservice.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import guru.springframework.trainingmsscbeerservice.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
