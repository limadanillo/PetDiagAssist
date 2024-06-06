package br.com.petdiagassist.core.useCases;


import br.com.petdiagassist.core.document.AuthDocument;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestLogin;
import br.com.petdiagassist.core.dtos.bpswf.response.AuthenticationResponse;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

/**
 * Interface Auth Service
 *
 * @author Danillo Lima
 * @since 16/02/2022
 */
public interface AuthService {
    Mono<AuthenticationResponse> generateToken(UserRequestLogin user);

    Mono saveTokenInBlacklist(AuthDocument auth);
}