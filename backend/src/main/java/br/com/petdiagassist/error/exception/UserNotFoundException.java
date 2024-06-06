package br.com.petdiagassist.error.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5331938412702355059L;
    private final BusinessErrorCode error;

    public UserNotFoundException(final BusinessErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public BusinessErrorCode getError() {
        return error;
    }
}
