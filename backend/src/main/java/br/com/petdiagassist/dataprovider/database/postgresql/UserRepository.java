package br.com.petdiagassist.dataprovider.database.postgresql;

import br.com.petdiagassist.core.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


/**
 * Class User Repository
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
@Repository
public interface  UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    /**
     * Find user by id and is active
     *
     * @param id       user id
     * @param isActive is active
     * @return user entity
     */
    Mono<UserEntity> findByIdAndIsActive(UUID id, Boolean isActive);

    Mono<UserEntity> findByEmail(String email);

    Flux<UserEntity> findAllBy(Pageable page);

    Mono<Long> countByIsActive(Boolean isActive);

    Mono<UserEntity> save(UserEntity user);

}
