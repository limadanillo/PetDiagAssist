package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.ReportRequest;
import reactor.core.publisher.Mono;

public interface ReportService {
    Mono createReport(ReportRequest reportRequest);
}
