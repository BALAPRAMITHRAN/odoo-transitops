package com.transitops.backend.application.usecase;

import com.transitops.backend.application.exception.MaintenanceLogNotFoundException;
import com.transitops.backend.application.exception.VehicleNotFoundException;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;

import java.time.LocalDateTime;

public class CloseMaintenanceLogUseCase {

    private final MaintenanceLogRepositoryPort maintenanceLogRepository;
    private final VehicleRepositoryPort vehicleRepository;

    public CloseMaintenanceLogUseCase(MaintenanceLogRepositoryPort maintenanceLogRepository,
                                      VehicleRepositoryPort vehicleRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public MaintenanceLog execute(Long logId) {
        MaintenanceLog log = maintenanceLogRepository.findById(logId)
                .orElseThrow(() -> new MaintenanceLogNotFoundException(logId));

        log.setStatus("CLOSED");
        log.setClosedAt(LocalDateTime.now());

        Vehicle vehicle = vehicleRepository.findById(log.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(log.getVehicleId()));

        if (!"Retired".equals(vehicle.getStatus())) {
            vehicle.setStatus("Available");
            vehicleRepository.save(vehicle);
        }

        return maintenanceLogRepository.save(log);
    }
}
