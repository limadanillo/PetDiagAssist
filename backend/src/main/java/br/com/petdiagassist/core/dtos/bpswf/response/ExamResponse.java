package br.com.petdiagassist.core.dtos.bpswf.response;

import br.com.petdiagassist.core.entity.ExamEntity;
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
public class ExamResponse {
    private UUID publicId;
    private String examType;
    private String expirationDate;
    private String imageReferenceId;
    private String imageType;
    private UUID animalId;
    private UUID creatorUserId;
    private String nameAnimal;
    private String nameUser;

    public ExamResponse(ExamEntity examEntity) {
        this.publicId = examEntity.getId();
        this.examType = examEntity.getExamType();
        this.expirationDate = DateUtils.parseDateToString(examEntity.getExpirationDate(), DateUtils.DATE_FORMATTER_BR);
        this.imageReferenceId = examEntity.getImageReferenceId();
        this.imageType =  examEntity.getImageType();
        this.animalId = examEntity.getAnimalId();
        this.creatorUserId = examEntity.getCreatorUserId();
    }

    public ExamResponse(ExamEntity examEntity, String nameAnimal, String nameUser) {
        this.publicId = examEntity.getId();
        this.examType = examEntity.getExamType();
        this.expirationDate = DateUtils.parseDateToString(examEntity.getExpirationDate(), DateUtils.DATE_FORMATTER_BR);
        this.imageReferenceId = examEntity.getImageReferenceId();
        this.imageType =  examEntity.getImageType();
        this.animalId = examEntity.getAnimalId();
        this.creatorUserId = examEntity.getCreatorUserId();
        this.nameAnimal = nameAnimal;
        this.nameUser = nameUser;
    }
}
