package br.com.petdiagassist.error;

import br.com.petdiagassist.error.exception.ForbiddenErrorException;
import br.com.petdiagassist.error.exception.UnauthorizedErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Class Default Error
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */

@Log4j2
@Component
@Order(-2)
public class DefaultError extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, options);
        Throwable throwable = getError(request);
        if (throwable instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) throwable;
            errorAttributesMap.put("message", ex.getMessage());
            return errorAttributesMap;
        }

        if (throwable instanceof UnauthorizedErrorException) {
            UnauthorizedErrorException ex = (UnauthorizedErrorException) throwable;
            errorAttributesMap.put("message", ex.getMessage());
            return errorAttributesMap;
        }

        if (throwable instanceof ForbiddenErrorException) {
            ForbiddenErrorException ex = (ForbiddenErrorException) throwable;
            errorAttributesMap.put("message", ex.getMessage());
            return errorAttributesMap;
        }
        return errorAttributesMap;
    }
}
