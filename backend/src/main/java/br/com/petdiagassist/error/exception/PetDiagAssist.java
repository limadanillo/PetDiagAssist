package br.com.petdiagassist.error.exception;

public enum PetDiagAssist implements BusinessErrorCode  {
    VETERINARIAN_NOT_FOUND("veterinarian.not.found.error"),
    USER_NOT_FOUND("user.not.found.error"),
    VETERINARIAN_ALREADY_EXISTS("veterinarian.already.exists.error"),
    ANIMAL_NOT_FOUND("animal.not.found.error");

    private final String message;

    PetDiagAssist(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
