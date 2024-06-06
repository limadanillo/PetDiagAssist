package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.UserProfileRequest;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPatch;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPost;
import br.com.petdiagassist.core.dtos.generic.response.ObjectResponse;
import br.com.petdiagassist.core.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Interface User Service
 *
 * @author Danillo Lima
 * @since 06/27/2021
 */
public interface UserService extends CrudService {

    Mono verifyUserByEmailOrActivateUser(UserRequestPost createdUser);

    Mono setUserData(UUID id, UserRequestPatch updatedUser);

    Mono<UserEntity> findByEmail(String email);

    Mono<Long> countByIsActive(boolean isActivated);
    Mono<ObjectResponse> getProfile(@NotNull String token);
    Mono updateUserProfile(@NotNull String token, @NotNull UserProfileRequest userProfileRequest);
}


