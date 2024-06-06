package br.com.petdiagassist.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

/**
 * Class Param Error Details
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@Builder
@Getter
@AllArgsConstructor
public class ParamErrorDetails implements Serializable {
    private static final long serialVersionUID = -8579990290309623052L;
    private final String message;
    private final String property;
    private final String value;
}
