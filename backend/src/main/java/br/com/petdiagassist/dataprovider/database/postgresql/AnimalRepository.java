package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.entity.AnimalEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface AnimalRepository extends ReactiveCrudRepository<AnimalEntity, UUID> {
    Flux<AnimalEntity> findAllBy(Pageable pageable);
}
