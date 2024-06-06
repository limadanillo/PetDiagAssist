package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.entity.VeterinarianEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VeterinarianRepository extends ReactiveCrudRepository<VeterinarianEntity, UUID> {
    Mono<VeterinarianEntity> findByUserId(UUID userId);
}
