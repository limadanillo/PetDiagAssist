package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.useCases.ExamVeterinarianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/exam-veterinarian")
@Tag(name = "Exam Veterinarian Controller", description = "Endpoints for managing exams related to veterinarians")
public class ExamVeterinarianController {

    private final ExamVeterinarianService examVeterinarianService;

    public ExamVeterinarianController(ExamVeterinarianService examVeterinarianService) {
        this.examVeterinarianService = examVeterinarianService;
    }

    @PostMapping("/assign/{idExam}/{idUser}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Assign an exam to a veterinarian", responses = {
            @ApiResponse(description = "Exam assigned", responseCode = "201"),
            @ApiResponse(description = "Exam not assigned", responseCode = "400", content = @Content)
    })
    public Mono assignExamToVeterinarian(@PathVariable UUID idExam, @PathVariable UUID idUser) {
        return examVeterinarianService.assignExamToVeterinarian(idExam, idUser);
    }
}
