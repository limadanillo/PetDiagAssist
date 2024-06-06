package br.com.petdiagassist.core.dtos.bpswf.request;

import br.com.petdiagassist.core.constraints.NameConstraint;
import br.com.petdiagassist.core.constraints.ValidDate;
import br.com.petdiagassist.core.constraints.ValidEnumValue;
import br.com.petdiagassist.core.enums.AnimalType;
import br.com.petdiagassist.core.enums.States;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AnimalRequest {
    @Getter
    @NameConstraint(person = "Usuário(a)", field = "name")
    private String name;

    @Getter
    @ValidEnumValue(targetClassType = AnimalType.class, message = "Tipo de animal inválido")
    private String type; // dog, cat, bird, etc

    @Getter // TODO - Add validation for breed depending on the type of animal
    private String breed;

    @Getter
    @ValidDate(customMessage = "Data de nascimento inválida")
    private String birthDate;

    @Getter
    private Float weight; // Peso
}
