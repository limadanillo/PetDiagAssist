package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.EmailConstraint;
import br.com.petdiagassist.core.constraints.NameConstraint;
import br.com.petdiagassist.core.constraints.PasswordConstraint;
import br.com.petdiagassist.core.constraints.RoleConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO User Update Request
 *
 * @author Danillo Lima
 * @since 06/21/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestPatch {

    @NameConstraint(person = "Usuário(a)", field = "first", require = false)
    private String name;

    @EmailConstraint(require = false)
    private String email;

    @RoleConstraint(require = false)
    private String role;

    private boolean isActive;
}
