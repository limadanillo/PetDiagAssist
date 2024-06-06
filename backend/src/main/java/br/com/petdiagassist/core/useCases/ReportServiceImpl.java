package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.ReportRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.ReportResponse;
import br.com.petdiagassist.core.entity.ReportEntity;
import br.com.petdiagassist.dataprovider.database.postgresql.ExamRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.ReportRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.VeterinarianRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ExamRepository examRepository;
    private final VeterinarianRepository veterinarianRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ExamRepository examRepository, VeterinarianRepository veterinarianRepository) {
        this.reportRepository = reportRepository;
        this.examRepository = examRepository;
        this.veterinarianRepository = veterinarianRepository;
    }

    @Override
    public Mono createReport(ReportRequest reportRequest) {
        var examEntityMono = examRepository.findById(reportRequest.getExamId());
        var veterinarianEntityMono = veterinarianRepository.findById(reportRequest.getVeterinarianId());
        return Mono.zip(examEntityMono, veterinarianEntityMono)
                .map(tuple -> {
                    var examEntity = tuple.getT1();
                    var veterinarianEntity = tuple.getT2();
                    return  new ReportEntity(reportRequest.getDescription(), examEntity.getId(), veterinarianEntity.getId());
                })
                .flatMap(reportRepository::save)
                .map(this::toResponse);
    }

    private ReportResponse toResponse(ReportEntity reportEntity) {
        return new ReportResponse(reportEntity);
    }
}
