package com.transitops.backend.application.usecase;

import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListVehiclesUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;

    public ListVehiclesUseCase(VehicleRepositoryPort vehicleRepository,
                               MaintenanceLogRepositoryPort maintenanceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    public List<Vehicle> listAll(String statusFilter) {
        if (statusFilter != null && !statusFilter.isBlank()) {
            return vehicleRepository.findByStatus(statusFilter);
        }
        return vehicleRepository.findAll();
    }

    public List<Vehicle> listAvailable() {
        return vehicleRepository.findByStatus("Available");
    }

    public List<MaintenanceLog> listMaintenanceLogs(Long vehicleId) {
        return maintenanceLogRepository.findByVehicleId(vehicleId);
    }

    public Map<String, Long> getDashboardSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalVehicles", vehicleRepository.countAll());
        summary.put("availableVehicles", vehicleRepository.countByStatus("Available"));
        summary.put("onTripVehicles", vehicleRepository.countByStatus("On Trip"));
        summary.put("inShopVehicles", vehicleRepository.countByStatus("In Shop"));
        summary.put("retiredVehicles", vehicleRepository.countByStatus("Retired"));
        return summary;
    }
}
