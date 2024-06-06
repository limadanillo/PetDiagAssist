package br.com.petdiagassist.error.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5331938412702355059L;
    private final BusinessErrorCode error;

    public BusinessException(final BusinessErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public BusinessErrorCode getError() {
        return error;
    }
}
