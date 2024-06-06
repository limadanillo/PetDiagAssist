package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.ExamRequest;
import br.com.petdiagassist.core.entity.ExamEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ExamService {
    Mono create(ExamRequest examRequest, @NotNull String token);

    Mono findById(UUID id);

    Mono<Void> delete(UUID id);

    Mono findExamByAnimalId(UUID animalId, Pageable pageable);

    Mono findAll(Pageable pageable);
}
