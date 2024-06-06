package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.config.security.JwtUtil;
import br.com.petdiagassist.core.dtos.bpswf.request.ExamRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.ExamResponse;
import br.com.petdiagassist.core.entity.AnimalEntity;
import br.com.petdiagassist.core.entity.ExamEntity;
import br.com.petdiagassist.core.entity.UserEntity;
import br.com.petdiagassist.dataprovider.database.postgresql.AnimalRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.ExamRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.UserRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final ImageStorageService imageStorageService;
    private final JwtUtil jwtUtil;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    public ExamServiceImpl(ExamRepository examRepository, ImageStorageService imageStorageService, JwtUtil jwtUtil,
                           AnimalRepository animalRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.imageStorageService = imageStorageService;
        this.jwtUtil = jwtUtil;
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<ExamResponse> create(ExamRequest examRequest, String token) {
        var userId = jwtUtil.getClaimsFromToken(token).get("publicId", String.class);
        return examRepository
                .save(new ExamEntity(examRequest, UUID.fromString(userId)))
                .map(ExamResponse::new);
    }

    @Override
    public Mono<ExamResponse> findById(UUID id) {
        return examRepository.findById(id).map(ExamResponse::new);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return examRepository.findById(id)
                .flatMap(examEntity -> {
                    return imageStorageService.delete(examEntity.getImageReferenceId())
                            .then(examRepository.delete(examEntity));
                });
    }

    @Override
    public Mono findExamByAnimalId(UUID animalId, Pageable pageable) {
        return examRepository.findByAnimalId(animalId, pageable)
                .map(ExamResponse::new)
                .collectList()
                .zipWith(this.examRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    @Override
    public Mono findAll(Pageable pageable) {
        return examRepository.findAllBy(pageable)
                .flatMap(exam -> {
                    Mono<AnimalEntity> animal = animalRepository.findById(exam.getAnimalId());
                    Mono<UserEntity> user = userRepository.findById(exam.getCreatorUserId());
                    return Mono.zip(animal, user)
                            .map(tuple -> new ExamResponse(exam, tuple.getT1().getName(), tuple.getT2().getName()));
                })
                .collectList()
                .zipWith(this.examRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }
}
