package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.document.AuthDocument;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestLogin;
import br.com.petdiagassist.core.dtos.generic.response.ObjectResponse;
import br.com.petdiagassist.core.useCases.AuthService;
import br.com.petdiagassist.dataprovider.adapter.generic.GenericConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Map;

/**
 * Class Authentication Controller
 *
 * @author Danillo Lima
 * @since 15/02/2022
 */
@RestController
@RequestMapping("api/auths")
@Validated
@Log4j2
@Tag(name = "Auth", description = "The Auth API")
public class AuthController {

    private final AuthService authService;

    private final GenericConverter genericConverter;

    private final MessageSource messageSource;

    public AuthController(AuthService authService, GenericConverter genericConverter, MessageSource messageSource) {
        this.authService = authService;
        this.genericConverter = genericConverter;
        this.messageSource = messageSource;
    }

    @PostMapping("/login")
    @Operation(security = {}) // Sobrescreve os requisitos de segurança para este endpoint
    public Mono login(@RequestBody UserRequestLogin user) {
       return authService.generateToken(user)
               .map(r -> ObjectResponse.builder()
                       .statusCode(HttpStatus.OK.value())
                       .data(r)
                       .build());
    }

    @PostMapping("/logout")
    public Mono logout(@NotNull @RequestHeader Map<String, String> headers) {
        AuthDocument auth = AuthDocument.builder().token(headers.get("Authorization").replace("Bearer ", "")).build();
        authService.saveTokenInBlacklist(auth);
        return genericConverter.converterMonoToObjectResponse(messageSource.getMessage("message.token.blocked.successfully", null, Locale.getDefault()), HttpStatus.OK);
    }
}
