package br.com.petdiagassist.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

/**
 * Class Exception Response
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@Data
@EqualsAndHashCode(callSuper=false)
public class ExceptionResponse  {
    private HttpStatus status;
    private Integer statusCode;
    private Object error;
    private Object params;
}
