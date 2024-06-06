package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.dtos.bpswf.request.ReportRequest;
import br.com.petdiagassist.core.useCases.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/reports")
@Tag(name = "Report Controller", description = "Endpoints for managing reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new report", responses = {
            @ApiResponse(description = "Report created", responseCode = "201"),
            @ApiResponse(description = "Report not created", responseCode = "400", content = @Content)
    })
    public Mono create(@RequestBody ReportRequest reportRequest) {
        return reportService.createReport(reportRequest);
    }
}
