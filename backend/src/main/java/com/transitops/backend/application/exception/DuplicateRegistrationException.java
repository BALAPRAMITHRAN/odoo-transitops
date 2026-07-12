package com.transitops.backend.application.exception;

public class DuplicateRegistrationException extends RuntimeException {

    public DuplicateRegistrationException(String registrationNumber) {
        super("Vehicle with registration number '" + registrationNumber + "' already exists");
    }
}
