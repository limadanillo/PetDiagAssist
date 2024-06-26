package br.com.petdiagassist.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

/**
 * Class generic for treatment of WebClient Error Response Exception,
 * because when there is no record it returns as family 4xx
 * falling in one exception
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Log4j2
@JsonIgnoreProperties({ "cause", "stackTrace", "suppressed", "localizedMessage", "message", "status"})
public class WebClientErrorResponseException extends Throwable {
    private static final long serialVersionUID = -1042815397974261032L;
    private Integer statusCode;
    private Object error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object params;

    public WebClientErrorResponseException() {
    }

    public WebClientErrorResponseException(Integer statusCode, Object error, Object params) {
        super("");
        this.statusCode = statusCode;
        this.error = error;
        this.params = params;
    }

    public WebClientErrorResponseException(Integer statusCode, Object error) {
        super("");
        this.statusCode = statusCode;
        this.error = error;
    }

    public WebClientErrorResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebClientErrorResponseException(Throwable cause) {
        super(cause);
    }

    public WebClientErrorResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
