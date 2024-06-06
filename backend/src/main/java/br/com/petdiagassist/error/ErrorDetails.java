package br.com.petdiagassist.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

/**
 * Class Error Details
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
@Builder
@Getter
@Data
@AllArgsConstructor
public class ErrorDetails implements Serializable {
    private static final long serialVersionUID = -4737922838431405101L;
    private final String name;
    private final String message;
    private final Long time;
}
