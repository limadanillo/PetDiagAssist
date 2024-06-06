package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.dtos.bpswf.response.ExamResponse;
import br.com.petdiagassist.core.entity.ExamEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ExamRepository extends ReactiveCrudRepository<ExamEntity, UUID> {
    Flux<ExamEntity> findByAnimalId(UUID animalId, Pageable pageable);
    Flux<ExamEntity> findAllBy(Pageable pageable);
}
