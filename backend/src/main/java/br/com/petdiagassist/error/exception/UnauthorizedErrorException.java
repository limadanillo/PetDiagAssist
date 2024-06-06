package br.com.petdiagassist.error.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class Unauthorized Error Exception
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedErrorException extends Throwable {

    private static final long serialVersionUID = -769147147883245023L;

    public UnauthorizedErrorException(String msg) {
        super(msg);
    }

    public UnauthorizedErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedErrorException(Throwable cause) {
        super(cause);
    }

}