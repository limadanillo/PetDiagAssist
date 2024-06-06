package br.com.petdiagassist.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("reports")
public class ReportEntity {
    @Id
    private UUID id;
    private String description;
    private UUID examId; // Assuming foreign key relationships are handled manually with exam
    private UUID veterinarianId;//
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReportEntity(String description, UUID examId, UUID veterinarianId) {
        this.description = description;
        this.examId = examId;
        this.veterinarianId = veterinarianId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    // Getters and Setters
}

