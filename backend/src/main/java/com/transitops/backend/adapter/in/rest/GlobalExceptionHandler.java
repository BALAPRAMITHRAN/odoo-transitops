package com.transitops.backend.adapter.in.rest;

import com.transitops.backend.application.exception.DuplicateRegistrationException;
import com.transitops.backend.application.exception.MaintenanceLogNotFoundException;
import com.transitops.backend.application.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegistrationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateRegistrationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleVehicleNotFound(VehicleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MaintenanceLogNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleLogNotFound(MaintenanceLogNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
