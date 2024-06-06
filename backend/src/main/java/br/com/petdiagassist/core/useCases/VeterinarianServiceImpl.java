package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.VeterinaryRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.VeterinaryResponse;
import br.com.petdiagassist.core.entity.VeterinarianEntity;
import br.com.petdiagassist.dataprovider.database.postgresql.UserRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.VeterinarianRepository;
import br.com.petdiagassist.error.exception.BusinessException;
import br.com.petdiagassist.error.exception.PetDiagAssist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class VeterinarianServiceImpl implements VeterinarianService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VeterinarianServiceImpl.class);

    private final VeterinarianRepository veterinaryRepository;
    private final UserRepository userRepository;

    public VeterinarianServiceImpl(VeterinarianRepository veterinaryRepository, UserRepository userRepository) {
        this.veterinaryRepository = veterinaryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Object> createVeterinary(VeterinaryRequest veterinaryRequest) {
        LOGGER.info("Creating veterinary");
        return userRepository.findById(veterinaryRequest.getPublicUserId())
                .flatMap(userEntity ->
                        veterinaryRepository.findByUserId(userEntity.getId())
                                .flatMap(veterinarianEntity -> {
                                    // Se um veterinário já existe, lança uma exceção, quebrando a cadeia.
                                    return Mono.error(new BusinessException(PetDiagAssist.VETERINARIAN_ALREADY_EXISTS));
                                })
                                .switchIfEmpty(Mono.defer(() ->
                                        // Cria um novo veterinário se não existir.
                                        veterinaryRepository.save(new VeterinarianEntity(veterinaryRequest, userEntity.getId()))
                                                .map(this::toDomain) // Garante que toDomain retorna VeterinaryResponse
                                ))
                )
                .switchIfEmpty(Mono.error(new BusinessException(PetDiagAssist.USER_NOT_FOUND))); // Garante que o erro é propagado corretamente
    }

    private VeterinaryResponse toDomain(VeterinarianEntity veterinarianEntity) {
        return new VeterinaryResponse(veterinarianEntity);
    }

    @Override
    public Mono getVeterinaryByUserId(UUID userId) {
        return veterinaryRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new BusinessException(PetDiagAssist.VETERINARIAN_NOT_FOUND)))
                .map(veterinarianEntity -> {
                    LOGGER.info("Veterinary found");
                    return toDomain(veterinarianEntity);
                });
    }

    @Override
    public Mono updateVeterinarian(UUID userId, VeterinaryRequest veterinaryRequest) {
        return veterinaryRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new BusinessException(PetDiagAssist.VETERINARIAN_NOT_FOUND)))
                .flatMap(veterinarianEntity -> {
                    veterinarianEntity.setUpdatedAt(LocalDateTime.now());
                    veterinarianEntity.setCouncilNumber(veterinaryRequest.getCouncilNumber());
                    veterinarianEntity.setState(veterinaryRequest.getCouncilState());
                    veterinarianEntity.setCouncilType(veterinaryRequest.getTypeCouncil());
                    return veterinaryRepository.save(veterinarianEntity)
                            .map(this::toDomain);
                });
    }

    @Override
    public Mono<Void> deleteVeterinarian(UUID userId) {
        return veterinaryRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new BusinessException(PetDiagAssist.VETERINARIAN_NOT_FOUND)))
                .flatMap(veterinaryRepository::delete);
    }
}
