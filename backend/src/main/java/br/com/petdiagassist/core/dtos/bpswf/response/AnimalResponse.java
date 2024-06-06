package br.com.petdiagassist.core.dtos.bpswf.response;

import br.com.petdiagassist.core.entity.AnimalEntity;
import br.com.petdiagassist.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalResponse {
    private UUID publicId;
    private String name;
    private String type;
    private String breed;
    private Float weight;
    private String birthDate;

    public AnimalResponse(AnimalEntity animalEntity) {
        this.publicId = animalEntity.getId();
        this.name = animalEntity.getName();
        this.type = animalEntity.getType();
        this.breed = animalEntity.getBreed();
        this.weight = animalEntity.getWeight();
        this.birthDate = DateUtils.parseDateToString(animalEntity.getBirthDate(), DateUtils.DATE_FORMATTER_BR);
    }
}
