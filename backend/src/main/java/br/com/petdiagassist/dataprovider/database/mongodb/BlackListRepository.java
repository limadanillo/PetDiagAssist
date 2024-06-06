package br.com.petdiagassist.dataprovider.database.mongodb;

import br.com.petdiagassist.core.document.AuthDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Class Black-List Repository
 *
 * @author Danillo Lima
 * @since 18/02/2022
 */

@Repository
public interface BlackListRepository extends ReactiveMongoRepository<AuthDocument, String> {
    Mono<AuthDocument> findByToken(String token);
    Mono<Boolean> existsByToken(String token);
}
