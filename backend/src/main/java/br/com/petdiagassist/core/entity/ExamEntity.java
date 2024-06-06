package br.com.petdiagassist.core.entity;

import br.com.petdiagassist.core.dtos.bpswf.request.ExamRequest;
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
@Table("exams")
public class ExamEntity {
    @Id
    private UUID id;
    private String examType;
    private UUID animalId;
    private LocalDate expirationDate;
    private String imageReferenceId;
    private String imageType; // Tipo da imagem (ex: jpg, png, etc)
    private UUID creatorUserId; // Identificador do usuário que criou o exame
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExamEntity(ExamRequest examRequest, UUID userId) {
        this.examType = examRequest.getExamType();
        this.animalId = UUID.fromString(examRequest.getAnimalId());
        this.expirationDate = DateUtils.parseStringLocalDate(examRequest.getExpirationDate());
        this.imageReferenceId = examRequest.getImageReferenceId();
        this.imageType = examRequest.getImageType();
        this.creatorUserId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
}

