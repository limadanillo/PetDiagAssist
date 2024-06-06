package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.config.security.JwtUtil;
import br.com.petdiagassist.core.dtos.bpswf.request.UserProfileRequest;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPatch;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPost;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPut;
import br.com.petdiagassist.core.dtos.bpswf.response.ExamResponse;
import br.com.petdiagassist.core.dtos.bpswf.response.UserResponse;
import br.com.petdiagassist.core.dtos.bpswf.response.VeterinaryResponse;
import br.com.petdiagassist.core.dtos.generic.response.ObjectResponse;
import br.com.petdiagassist.core.entity.AnimalEntity;
import br.com.petdiagassist.core.entity.UserEntity;
import br.com.petdiagassist.core.entity.VeterinarianEntity;
import br.com.petdiagassist.dataprovider.adapter.generic.GenericConverter;
import br.com.petdiagassist.dataprovider.database.postgresql.UserRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.VeterinarianRepository;
import br.com.petdiagassist.error.exception.BadRequestErrorException;
import br.com.petdiagassist.utils.StringUtils;
import br.com.petdiagassist.utils.UtilsMonoZip;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Class User Service
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final MessageSource messageSource;

    private final UserRepository userRepository;

    private final GenericConverter genericConverter;

    private final PasswordEncoder encoder;

    private final JwtUtil jwtUtil;

    private final VeterinarianRepository veterinarianRepository;

    public UserServiceImpl(MessageSource messageSource,
                           UserRepository userRepository,
                           GenericConverter genericConverter,
                           PasswordEncoder encoder,
                           JwtUtil jwtUtil,
                           VeterinarianRepository veterinarianRepository) {
        this.messageSource = messageSource;
        this.userRepository = userRepository;
        this.genericConverter = genericConverter;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.veterinarianRepository = veterinarianRepository;
    }

    public Mono<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono findById(UUID id) {
        return userRepository.findByIdAndIsActive(id, true).map(user ->
                genericConverter.converterObjectToObject(user, UserResponse.class)
        ).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault()))));
    }

    public Mono findAll(Pageable page) {
        return userRepository.findAllBy(page)
                .map(user -> new UserResponse(user, Optional.empty()))
                .collectList()
                .zipWith(this.userRepository.count())
                .map(p -> new PageImpl<>(p.getT1(), page, p.getT2()));
    }

    public Mono<Long> countByIsActive(boolean isActivated) {
        return userRepository.countByIsActive(true);
    }

    public Mono save(Object obj) {
        return verifyUserByEmailOrActivateUser((UserRequestPost) obj)
                .switchIfEmpty(Mono.defer(() -> {
                    var user = new UserEntity((UserRequestPost) obj);
                    return this.persisteUser(user);
                }));
    }

    @Override
    public Mono updateByPut(Object updatedUser) {
        return userRepository.findById(((UserRequestPut) updatedUser).getId()).flatMap(oldUser -> {
            if (!oldUser.getEmail().equals(((UserRequestPut) updatedUser).getEmail()))
                return Mono.error(new BadRequestErrorException(
                        messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault()))
                );
            UserEntity user = genericConverter.converterObjectToObject(updatedUser, UserEntity.class);
            return this.persisteUser(user);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(
                messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault())))
        );
    }

    @Override
    public Mono updateByPatch(UUID id, UserRequestPatch updatedUser) {
        return setUserData(id, updatedUser)
                .flatMap(userRepository::save)
                .map(userDb -> new UserResponse(userDb, Optional.empty()));
    }

    @Transactional
    public Mono delete(UUID id) {
        return userRepository.findByIdAndIsActive(id, true).flatMap(oldUser -> {
            oldUser.setIsActive(false);
            return userRepository.save(oldUser);
        }).switchIfEmpty(Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault()))));
    }

    public Mono verifyUserByEmailOrActivateUser(UserRequestPost createdUser) {
        return userRepository.findByEmail(createdUser.getEmail()).flatMap(existingUser -> {
            if (existingUser.getIsActive())
                return Mono.error(new BadRequestErrorException(
                        messageSource.getMessage("message.bad.request.error.existing.user", null, Locale.getDefault()))
                );
            return updateByPatch(existingUser.getId(), genericConverter.converterObjectToObject(createdUser, UserRequestPatch.class));
        });
    }

    public Mono<UserEntity> setUserData(UUID id, UserRequestPatch updatedUser) {
        return userRepository.findById(id)
                .flatMap(oldUser -> {
                    setIfNotNullOrBlank(oldUser::setName, updatedUser.getName());
                    setIfNotNullOrBlank(oldUser::setRole, updatedUser.getRole());
                    if (!StringUtils.isNullOrBlank(updatedUser.getEmail())) {
                        if (!oldUser.getEmail().equals(updatedUser.getEmail()))
                            return Mono.error(new BadRequestErrorException(
                                    messageSource.getMessage("message.bad.request.error.email", null, Locale.getDefault()))
                            );
                        oldUser.setEmail(updatedUser.getEmail());
                    }
                    oldUser.setIsActive(updatedUser.isActive());
                    setIfNotNullOrBlank(oldUser::setRole, updatedUser.getRole());
                    return Mono.just(oldUser);
                });
    }

    private void setIfNotNullOrBlank(Consumer<String> setter, String value) {
        if (StringUtils.isNullOrBlank(value)) {
            return;
        }
        setter.accept(value);
    }

    private Mono persisteUser(UserEntity user) {
        if (!StringUtils.isNullOrBlank(user.getPassword()))
            user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user).map(userDb ->
                genericConverter.converterObjectToObject(userDb, UserResponse.class)
        );
    }

    public Mono<ObjectResponse> getProfile(@NotNull String token) {
        var userId = jwtUtil.getClaimsFromToken(token).get("publicId", String.class);
        var userEntityMono = userRepository.findById(UUID.fromString(userId))
                .transform(UtilsMonoZip::optional);
        var veterinarianEntityMono = veterinarianRepository.findByUserId(UUID.fromString(userId))
                .transform(UtilsMonoZip::optional);

        return Mono.zip(userEntityMono, veterinarianEntityMono)
                .map(tuple -> {
                    // Create a new UserResponse and set the user and veterinarian data
                    AtomicReference<UserResponse> userResponse = new AtomicReference<>(new UserResponse());
                    tuple.getT2().ifPresentOrElse(veterinarian -> {
                        var veterinarianResponse = genericConverter.converterObjectToObject(veterinarian, VeterinaryResponse.class);
                        userResponse.set(new UserResponse(tuple.getT1().get(), Optional.ofNullable(veterinarianResponse)));
                    }, () -> userResponse.set(new UserResponse(tuple.getT1().get(), Optional.empty())));

                    // Create a new ObjectResponse and set the user and veterinarian data
                    return ObjectResponse.builder()
                            .statusCode(HttpStatus.OK.value())
                            .data(userResponse.get())
                            .build();
                });
    }

    @Override
    public Mono<Object> updateUserProfile(String token, UserProfileRequest userProfileRequest) {
        var userId = jwtUtil.getClaimsFromToken(token).get("publicId", String.class);
        if(UUID.fromString(userId).equals(userProfileRequest.getPublicId())) {
            return userRepository.findById(UUID.fromString(userId))
                    .flatMap(user -> {
                        user.setName(userProfileRequest.getName());
                        user.setEmail(userProfileRequest.getEmail());
                        user.setPassword(userProfileRequest.getPassword());
                        return userRepository.save(user)
                                .map(userDb -> {
                                    return ObjectResponse.builder()
                                            .statusCode(HttpStatus.OK.value())
                                            .data(new UserResponse(userDb, Optional.empty()))
                                            .build();
                                });
                    });
        }

        return Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.user", null, Locale.getDefault())));
    }
}
