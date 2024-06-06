package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.EmailConstraint;
import br.com.petdiagassist.core.constraints.PasswordConstraint;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

/**
 * Class DTO User Login Request
 *
 * @author Danillo Lima
 * @since 03/09/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestLogin {

    @NotEmpty(message = "Email must not be empty")
    @EmailConstraint
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @PasswordConstraint
    private String password;
}