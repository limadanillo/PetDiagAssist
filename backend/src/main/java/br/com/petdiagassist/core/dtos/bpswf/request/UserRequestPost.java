package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.EmailConstraint;
import br.com.petdiagassist.core.constraints.NameConstraint;
import br.com.petdiagassist.core.constraints.PasswordConstraint;
import br.com.petdiagassist.core.constraints.PhoneConstraint;
import br.com.petdiagassist.core.constraints.RoleConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO User Create Request
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestPost {

    @NameConstraint(person = "Usuário(a)", field = "name")
    private String name;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    @PhoneConstraint
    private String phone;

    @RoleConstraint
    private String role;
}
