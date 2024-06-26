package br.com.petdiagassist.error;

import br.com.petdiagassist.utils.ErrorUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * Class Authentication Error
 *
 * @author Danillo Lima
 * @since 06/28/2021
 */
@Log4j2
@Component
public class AuthenticationError implements ServerAuthenticationEntryPoint {

    @Autowired
    private MessageSource messageSource;

    @SneakyThrows
    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException authException) {
        return  ErrorUtils.responseFailed(serverWebExchange, HttpStatus.UNAUTHORIZED.value(),
                messageSource.getMessage("message.error.not.authenticated", null, Locale.getDefault()), "UnauthorizedError");
    }
}
