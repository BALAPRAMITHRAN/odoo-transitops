package com.transitops.backend.application.exception;

public class MaintenanceLogNotFoundException extends RuntimeException {

    public MaintenanceLogNotFoundException(Long id) {
        super("Maintenance log with id " + id + " not found");
    }
}
