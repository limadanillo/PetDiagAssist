package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.AnimalRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.AnimalResponse;
import br.com.petdiagassist.core.entity.AnimalEntity;
import br.com.petdiagassist.dataprovider.database.postgresql.AnimalRepository;
import br.com.petdiagassist.error.exception.BusinessException;
import br.com.petdiagassist.error.exception.PetDiagAssist;
import br.com.petdiagassist.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AnimalServiceImpl implements AnimalService{
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Mono createAnimal(AnimalRequest animalRequest) {
        return animalRepository.save(new AnimalEntity(animalRequest))
                .map(this::toDomain);
    }

    private AnimalResponse toDomain(AnimalEntity animalEntity) {
        return new AnimalResponse(animalEntity);
    }

    @Override
    public Mono getAnimalById(UUID id) {
        return animalRepository.findById(id)
                .map(this::toDomain)
                .switchIfEmpty(Mono.error(new BusinessException(PetDiagAssist.ANIMAL_NOT_FOUND)));
    }

    @Override
    public Mono updateAnimal(UUID id, AnimalRequest animalRequest) {
        return animalRepository.findById(id)
                .flatMap(animalEntity -> {
                    animalEntity.setName(animalRequest.getName());
                    animalEntity.setType(animalRequest.getType());
                    animalEntity.setBreed(animalRequest.getBreed());
                    animalEntity.setWeight(animalRequest.getWeight());
                    animalEntity.setBirthDate(DateUtils.parseStringLocalDate(animalRequest.getBirthDate()));
                    animalEntity.setUpdatedAt(LocalDateTime.now());
                    return animalRepository.save(animalEntity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono deleteAnimal(UUID userId) {
        return animalRepository.deleteById(userId);
    }

    @Override
    public Mono<Page<AnimalResponse>> getAllAnimals(Pageable pageable) {
        return animalRepository.findAllBy(pageable)
                .map(this::toDomain)
                .collectList()
                .zipWith(this.animalRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }
}
