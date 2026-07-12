package com.transitops.backend.application.port;

import com.transitops.backend.domain.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryPort {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    List<Vehicle> findAll();

    List<Vehicle> findByStatus(String status);

    long countByStatus(String status);

    long countAll();
}
