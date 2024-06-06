package br.com.petdiagassist.core.dtos.bpswf.response;

import br.com.petdiagassist.core.entity.UserEntity;
import br.com.petdiagassist.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Class DTO User Response
 *
 * @author Danillo Lima
 * @since 16/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID publicId;
    private String name;
    private String email;
    private String role;
    private Boolean isActive;
    private String createdAt;
    private String updatedAt;
    private VeterinaryResponse veterinary;

    public UserResponse(UserEntity userEntity, Optional<VeterinaryResponse> veterinaryResponse) {
        this.publicId = userEntity.getId();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
        this.isActive = userEntity.getIsActive();
        this.createdAt = DateUtils.parseDateToString(userEntity.getCreatedAt(), DateUtils.DATE_TIME_FORMATTER_BR);
        this.updatedAt = DateUtils.parseDateToString(userEntity.getUpdatedAt(), DateUtils.DATE_TIME_FORMATTER_BR);
        veterinaryResponse.ifPresent(this::setVeterinary);
    }
}
