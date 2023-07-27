package com.odeyalo.sonata.artists.entity;

public enum RegistrationVerificationStatus {
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    FAILED("failed");

    private final String name;

    RegistrationVerificationStatus(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
