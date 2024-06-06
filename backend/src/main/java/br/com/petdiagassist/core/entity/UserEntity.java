package br.com.petdiagassist.core.entity;

import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class User Entity
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("users")
public class UserEntity implements GenericEntity {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String role;
    private Boolean isActive;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public UserEntity(UserRequestPost userRequestPost) {
        this.name = userRequestPost.getName();
        this.email = userRequestPost.getEmail();
        this.password = userRequestPost.getPassword();
        this.role = userRequestPost.getRole();
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}