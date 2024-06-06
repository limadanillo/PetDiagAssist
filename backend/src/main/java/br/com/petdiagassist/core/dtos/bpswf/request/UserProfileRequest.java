package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.EmailConstraint;
import br.com.petdiagassist.core.constraints.NameConstraint;
import br.com.petdiagassist.core.constraints.PasswordConstraint;
import br.com.petdiagassist.core.constraints.RoleConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

/**
 * Class DTO User Profile Request
 *
 * @author Danillo
 * @since 06/07/2021
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserProfileRequest {

    @UUID(message = "Invalid public id")
    private String publicId;

    @Getter
    @NameConstraint(person = "Usuário(a)", field = "name")
    private String name;

    @Getter
    @EmailConstraint
    private String email;

    @Getter
    @PasswordConstraint
    private String password;

    @Getter
    @RoleConstraint
    private String role;

    @Getter
    private VeterinaryRequest veterinaryRequest;

    public java.util.UUID getPublicId() {
        return java.util.UUID.fromString(publicId);
    }
}
