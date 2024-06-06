package br.com.petdiagassist.core.entity.enums;

public enum CouncilType {
    CRMV("veterinary"),
    OTHER("other");

    private final String councilType;

    CouncilType(String councilType) {
        this.councilType = councilType;
    }

    public String getCouncilType() {
        return councilType;
    }
}
