package com.transitops.backend.application.usecase;

import com.transitops.backend.application.exception.DuplicateRegistrationException;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.Vehicle;

public class RegisterVehicleUseCase {

    private final VehicleRepositoryPort vehicleRepository;

    public RegisterVehicleUseCase(VehicleRepositoryPort vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle execute(Vehicle vehicle) {
        vehicleRepository.findByRegistrationNumber(vehicle.getRegistrationNumber())
                .ifPresent(existing -> {
                    throw new DuplicateRegistrationException(vehicle.getRegistrationNumber());
                });

        if (vehicle.getStatus() == null || vehicle.getStatus().isBlank()) {
            vehicle.setStatus("Available");
        }

        if (vehicle.getHealthScore() == null) {
            vehicle.setHealthScore(100);
        }

        if (vehicle.getPriority() == null || vehicle.getPriority().isBlank()) {
            vehicle.setPriority("Medium");
        }

        return vehicleRepository.save(vehicle);
    }
}
