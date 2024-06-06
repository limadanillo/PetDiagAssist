package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.dtos.bpswf.request.ExamRequest;
import br.com.petdiagassist.core.useCases.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/exams")
@Tag(name = "Exam", description = "The Exam API")
@Validated
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new exam", responses = {
            @ApiResponse(description = "Exam created", responseCode = "201"),
            @ApiResponse(description = "Exam not created", responseCode = "400", content = @Content)
    })
    public Mono create(@NotNull @RequestHeader Map<String, String> headers,
                       @RequestBody ExamRequest examRequest) {
        return examService.create(examRequest, headers.get("Authorization"));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all exams", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content)
    })
    public Mono list(@RequestParam(required = false, defaultValue = "0") String page, @RequestParam(required = false, defaultValue = "10") String size) {
        return examService.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(size)));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a single exam by ID", responses = {
            @ApiResponse(description = "Exam found", responseCode = "200"),
            @ApiResponse(description = "Exam not found", responseCode = "404", content = @Content)
    })
    public Mono getExamById(@PathVariable UUID id) {
        return examService.findById(id);
    }

    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all exams by animal", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content)
    })
    public Mono listExamsByAnimal(@PathVariable UUID id,
                                  @RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return examService.findExamByAnimalId(id, pageRequest);
    }
}
