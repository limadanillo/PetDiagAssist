package br.com.petdiagassist.error.exception;

import java.io.Serializable;

/**
 * Interface padrão para códigos de erro de negócio
 */
public interface BusinessErrorCode extends Serializable {
    /**
     * Get the error code
     *
     * @return the error code
     */
    String getCode();

    /**
     * Get the error message
     *
     * @return the error message
     */
    String getMessage();
}
