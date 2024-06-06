package br.com.petdiagassist.core.dtos.bpswf.response;

import br.com.petdiagassist.core.entity.ReportEntity;
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
public class ReportResponse {
    private UUID publicId;
    private String description;
    private UUID examId;
    private UUID veterinarianId;
    private String createdAt;
    private String updatedAt;

    public ReportResponse(ReportEntity reportEntity) {
        this.publicId = reportEntity.getId();
        this.description = reportEntity.getDescription();
        this.examId = reportEntity.getExamId();
        this.veterinarianId = reportEntity.getVeterinarianId();
        this.createdAt = DateUtils.parseDateToString(reportEntity.getCreatedAt(), DateUtils.DATE_TIME_FORMATTER_BR);
        this.updatedAt = DateUtils.parseDateToString(reportEntity.getUpdatedAt(), DateUtils.DATE_TIME_FORMATTER_BR);;
    }
}
