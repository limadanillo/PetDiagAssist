package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.AnimalRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.AnimalResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AnimalService {
    Mono createAnimal(AnimalRequest animalRequest);
    Mono getAnimalById(UUID id);
    Mono updateAnimal(UUID id, AnimalRequest animalRequest);
    Mono deleteAnimal(UUID userId);
    Mono getAllAnimals(Pageable pageable);
}
