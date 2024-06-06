package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.ValidEnumValue;
import br.com.petdiagassist.core.enums.CouncilType;
import br.com.petdiagassist.core.enums.States;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

/**
 * Class DTO Veterinary Request
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VeterinaryRequest {

    private UUID publicUserId;

    @NotEmpty(message = "Número do conselho não pode ser vazio")
    @Pattern(regexp = "^\\d{6}$", message = "O número do conselho deve ter 6 dígitos")
    private String councilNumber;

    @ValidEnumValue(targetClassType = States.class, message = "Estado inválido")
    private String councilState;

    @ValidEnumValue(targetClassType = CouncilType.class, message = "Tipo de conselho inválido")
    private String typeCouncil;
}