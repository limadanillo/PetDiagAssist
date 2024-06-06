package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.dtos.bpswf.request.VeterinaryRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.VeterinaryResponse;
import br.com.petdiagassist.core.useCases.VeterinarianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/veterinarians")
@Tag(name = "Veterinarian", description = "The Veterinarian API")
public class VeterinarianController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VeterinarianController.class);

    private final VeterinarianService veterinarianService;

    public VeterinarianController(VeterinarianService veterinarianService) {
        this.veterinarianService = veterinarianService;
    }

    @GetMapping
    @Operation(summary = "List all veterinarians", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(schema = @Schema(implementation = VeterinaryResponse.class)))
    })
    public Flux<VeterinaryResponse> getAllVeterinarians() {
        return Flux.empty();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get a single veterinarian by USER ID", responses = {
            @ApiResponse(description = "Veterinarian found", responseCode = "200"),
            @ApiResponse(description = "Veterinarian not found", responseCode = "404", content = @Content)
    })
    public Mono getVeterinarianById(@PathVariable UUID id) {
        return veterinarianService.getVeterinaryByUserId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new veterinarian", responses = {
            @ApiResponse(description = "Veterinarian created", responseCode = "201"),
            @ApiResponse(description = "Veterinarian not created", responseCode = "400", content = @Content)
    })
    public Mono createVeterinarian(@RequestBody VeterinaryRequest veterinarian) {
        return veterinarianService.createVeterinary(veterinarian);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a veterinarian by ID", responses = {
            @ApiResponse(description = "Veterinarian updated", responseCode = "200"),
            @ApiResponse(description = "Veterinarian not found", responseCode = "404", content = @Content)
    })
    public Mono updateVeterinarian(@PathVariable String id, @RequestBody VeterinaryRequest veterinarian) {
        return veterinarianService.updateVeterinarian(UUID.fromString(id), veterinarian)
                .map(updatedVet -> ResponseEntity.ok(updatedVet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a veterinarian by ID", responses = {
            @ApiResponse(description = "Veterinarian deleted", responseCode = "204"),
            @ApiResponse(description = "Veterinarian not found", responseCode = "404", content = @Content)
    })
    public Mono<ResponseEntity<Void>> deleteVeterinarian(@PathVariable String id) {
        return veterinarianService.deleteVeterinarian(UUID.fromString(id))
                .map(r -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

