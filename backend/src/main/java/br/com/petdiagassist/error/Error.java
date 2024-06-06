package br.com.petdiagassist.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.List;

/**
 * Class Error
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@Builder
@Getter
public class Error implements Serializable {
    private final int statusCode;
    private final ErrorDetails error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<ParamErrorDetails> params;
}
