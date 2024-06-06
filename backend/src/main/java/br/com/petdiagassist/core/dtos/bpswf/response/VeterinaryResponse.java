package br.com.petdiagassist.core.dtos.bpswf.response;

import br.com.petdiagassist.core.entity.VeterinarianEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeterinaryResponse {
    private UUID publicId;
    private String councilNumber;
    private String councilState;
    private String councilType;

    public VeterinaryResponse(VeterinarianEntity veterinarianEntity) {
        this.publicId = veterinarianEntity.getId();
        this.councilNumber = veterinarianEntity.getCouncilNumber();
        this.councilState = veterinarianEntity.getState();
        this.councilType = veterinarianEntity.getCouncilType();
    }
}
