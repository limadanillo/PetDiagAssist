package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPatch;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Interface CRUD Service
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
public interface CrudService {
    Mono findById(UUID id);
    Mono findAll(Pageable page);
    Mono save(Object obj);
    Mono updateByPut(Object obj);
    Mono updateByPatch(UUID id, UserRequestPatch obj);
    Mono delete(UUID id);
}
