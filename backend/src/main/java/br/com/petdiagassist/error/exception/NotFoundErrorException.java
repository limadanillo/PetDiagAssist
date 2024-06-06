package br.com.petdiagassist.error.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Class Not Found Error Exception
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundErrorException extends Throwable {

    @Serial
    private static final long serialVersionUID = -769147155483245022L;

    public NotFoundErrorException(String msg) {
        super(msg);
    }

    public NotFoundErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundErrorException(Throwable cause) {
        super(cause);
    }

}