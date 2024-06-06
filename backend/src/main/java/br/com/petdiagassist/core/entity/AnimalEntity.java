package br.com.petdiagassist.core.entity;

import br.com.petdiagassist.core.dtos.bpswf.request.AnimalRequest;
import br.com.petdiagassist.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("animals")
public class AnimalEntity implements GenericEntity {
    @Id
    private UUID id;
    private String name;
    private String type; // dog, cat, bird, etc
    private String breed;// Raça
    private Float weight; // Peso
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnimalEntity(AnimalRequest animalRequest) {
        this.name = animalRequest.getName();
        this.type = animalRequest.getType();
        this.breed = animalRequest.getBreed();
        this.weight = animalRequest.getWeight();
        this.birthDate = DateUtils.parseStringLocalDate(animalRequest.getBirthDate());
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    // Getters and Setters
}
