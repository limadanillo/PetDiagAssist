package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.dtos.bpswf.request.AnimalRequest;
import br.com.petdiagassist.core.dtos.bpswf.response.AnimalResponse;
import br.com.petdiagassist.core.useCases.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animal", description = "The Animal API")
public class AnimalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalController.class);

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new animal", responses = {
            @ApiResponse(description = "Animal created", responseCode = "201"),
            @ApiResponse(description = "Animal not created", responseCode = "400", content = @Content)
    })
    public Mono create(@RequestBody AnimalRequest animalRequest) {
        LOGGER.info("Creating animal");
        return animalService.createAnimal(animalRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single animal by ID", responses = {
            @ApiResponse(description = "Animal found", responseCode = "200"),
            @ApiResponse(description = "Animal not found", responseCode = "404", content = @Content)
    })
    public Mono getAnimalById(@PathVariable UUID id) {
        return animalService.getAnimalById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an animal", responses = {
            @ApiResponse(description = "Animal updated", responseCode = "200"),
            @ApiResponse(description = "Animal not updated", responseCode = "400", content = @Content)
    })
    public Mono updateAnimal(@RequestBody AnimalRequest animalRequest, @PathVariable UUID id) {
        return animalService.updateAnimal(id, animalRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an animal", responses = {
            @ApiResponse(description = "Animal deleted", responseCode = "200"),
            @ApiResponse(description = "Animal not deleted", responseCode = "400", content = @Content)
    })
    public Mono deleteAnimal(@PathVariable UUID id) {
        return animalService.deleteAnimal(id);
    }

    @GetMapping
    @Operation(summary = "List all animals", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(schema = @Schema(implementation = AnimalResponse.class)))
    })
    public Mono getAllAnimals(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return animalService.getAllAnimals(PageRequest.of(page, size));
    }
}
