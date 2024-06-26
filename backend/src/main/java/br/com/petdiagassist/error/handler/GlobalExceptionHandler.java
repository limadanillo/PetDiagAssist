package br.com.petdiagassist.error.handler;

import br.com.petdiagassist.error.Error;
import br.com.petdiagassist.error.ErrorDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

/**
 * Class Global Exception Handler
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties.Resources resourceProperties,
                                  ApplicationContext applicationContext,
                                  ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        String msg = null;
        String query = request.uri().getQuery();
        ErrorAttributeOptions errorAttributeOptions = isTraceEnabled(query) ? of(Include.STACK_TRACE) : defaults();

        Map<String, Object> errorAttributesMap = getErrorAttributes(request, errorAttributeOptions);
        int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);
        if (errorAttributesMap.get("message") != null)
            errorAttributesMap.get("message").toString();
        if (status == 500) {
            msg = messageSource.getMessage("message.internal.server.error", null, Locale.getDefault());
        }
        Error error = Error.builder().statusCode(status)
                .error(ErrorDetails.builder()
                        .message(msg)
                        .name(HttpStatus.valueOf(status).name()).time(new Date().getTime()).build())
                .build();
        log.error(error.getError().getMessage());
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(error));
    }

    private boolean isTraceEnabled(String query) {
        return !StringUtils.isEmpty(query) && query.contains("trace=true");
    }
}
