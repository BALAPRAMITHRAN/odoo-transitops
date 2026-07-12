package com.transitops.backend.application.usecase;

import com.transitops.backend.application.exception.VehicleNotFoundException;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;

import java.time.LocalDateTime;

public class CreateMaintenanceLogUseCase {

    private final MaintenanceLogRepositoryPort maintenanceLogRepository;
    private final VehicleRepositoryPort vehicleRepository;

    public CreateMaintenanceLogUseCase(MaintenanceLogRepositoryPort maintenanceLogRepository,
                                       VehicleRepositoryPort vehicleRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public MaintenanceLog execute(Long vehicleId, MaintenanceLog log) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        log.setVehicleId(vehicleId);
        log.setStatus("ACTIVE");
        log.setCreatedAt(LocalDateTime.now());

        vehicle.setStatus("In Shop");
        vehicleRepository.save(vehicle);

        return maintenanceLogRepository.save(log);
    }
}
