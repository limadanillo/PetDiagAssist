package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.EmailConstraint;
import br.com.petdiagassist.core.constraints.NameConstraint;
import br.com.petdiagassist.core.constraints.PasswordConstraint;
import br.com.petdiagassist.core.constraints.RoleConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class UserRequestPut {

    @NotNull
    private UUID id;

    @NameConstraint(person = "Usuário(a)", field = "first")
    private String name;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    @RoleConstraint
    private String role;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
}
