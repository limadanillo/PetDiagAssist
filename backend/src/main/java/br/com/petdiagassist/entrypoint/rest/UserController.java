package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.dtos.bpswf.request.UserProfileRequest;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPatch;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPost;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestPut;
import br.com.petdiagassist.core.dtos.bpswf.response.UserResponse;
import br.com.petdiagassist.core.dtos.generic.response.ObjectResponse;
import br.com.petdiagassist.core.useCases.UserService;
import br.com.petdiagassist.dataprovider.adapter.generic.GenericConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Class User Controller
 *
 * @author Danillo Lima
 * @since 06/07/2021
 */

@RestController
@RequestMapping("api/users")
@Validated
@Log4j2
@Tag(name = "User", description = "The User API")
public class UserController {

    private final UserService userService;

    private final GenericConverter genericConverter;

    private final MessageSource messageSource;

    public UserController(UserService userService, GenericConverter genericConverter, MessageSource messageSource) {
        this.userService = userService;
        this.genericConverter = genericConverter;
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a single user by ID", responses = {
            @ApiResponse(description = "User found", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404", content = @Content)
    })
    public Mono<ObjectResponse> findById(@PathVariable UUID id) {
        return userService.findById(id).flatMap(user -> genericConverter.converterMonoToObjectResponse(user, HttpStatus.OK));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all users", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content)
    })
    public Mono findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userService.findAll(pageRequest);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(description = "User created", responseCode = "201"),
            @ApiResponse(description = "User not created", responseCode = "400", content = @Content)
    })
    public Mono save(@RequestBody @Valid UserRequestPost user) {
        Mono<UserResponse> userResponse = userService.save(user);
        return userResponse.flatMap(userResp -> genericConverter.converterMonoToObjectResponse(userResp, HttpStatus.CREATED));
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a user by ID", responses = {
            @ApiResponse(description = "User updated", responseCode = "200"),
            @ApiResponse(description = "User not updated", responseCode = "400", content = @Content)
    })
    public Mono updateByPatch(@PathVariable UUID id, @RequestBody @Valid UserRequestPatch user) {
        return userService.updateByPatch(id, user);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a user by ID", responses = {
            @ApiResponse(description = "User deleted", responseCode = "200"),
            @ApiResponse(description = "User not deleted", responseCode = "400", content = @Content)
    })
    public Mono delete(@PathVariable UUID id) {
        return userService.delete(id)
                .flatMap(resp -> genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.user.deleted.successfully", null, Locale.getDefault()), HttpStatus.OK));
    }

    @GetMapping(path = "/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user profile", responses = {
            @ApiResponse(description = "User profile found", responseCode = "200"),
            @ApiResponse(description = "User profile not found", responseCode = "404", content = @Content)
    })
    public Mono<ObjectResponse> getProfile(@NotNull @RequestHeader Map<String, String> headers) {
        return userService.getProfile(headers.get("Authorization"));
    }

    @PutMapping(path = "/profile")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user profile", responses = {
            @ApiResponse(description = "User profile updated", responseCode = "200"),
            @ApiResponse(description = "User profile not updated", responseCode = "400", content = @Content)
    })
    public Mono updatedProfile(@NotNull @RequestHeader Map<String, String> headers,
                               @RequestBody @Valid UserProfileRequest userProfileRequest) {
        return userService.updateUserProfile(headers.get("Authorization"), userProfileRequest);
    }
}
