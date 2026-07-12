package com.transitops.backend.application.usecase;

import com.transitops.backend.adapter.in.rest.dto.ActionCenterDTO;
import com.transitops.backend.adapter.in.rest.dto.FleetHealthDTO;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionCenterUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;
    private final VehicleHealthScoreUseCase vehicleHealthScoreUseCase;

    public ActionCenterUseCase(VehicleRepositoryPort vehicleRepository,
                               MaintenanceLogRepositoryPort maintenanceLogRepository,
                               VehicleHealthScoreUseCase vehicleHealthScoreUseCase) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.vehicleHealthScoreUseCase = vehicleHealthScoreUseCase;
    }

    public ActionCenterDTO execute() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        FleetHealthDTO fleetHealth = vehicleHealthScoreUseCase.execute();

        List<ActionCenterDTO.VehicleMaintenanceItem> vehiclesRequiringMaintenance = calculateVehiclesRequiringMaintenance(allVehicles);
        List<ActionCenterDTO.VehiclePriorityItem> highestPriorityVehicles = calculateHighestPriorityVehicles(fleetHealth);
        List<ActionCenterDTO.VehicleUpcomingItem> upcomingMaintenance = calculateUpcomingMaintenance(allVehicles);

        return new ActionCenterDTO(vehiclesRequiringMaintenance, highestPriorityVehicles, upcomingMaintenance);
    }

    private List<ActionCenterDTO.VehicleMaintenanceItem> calculateVehiclesRequiringMaintenance(List<Vehicle> vehicles) {
        List<ActionCenterDTO.VehicleMaintenanceItem> result = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if ("Retired".equals(vehicle.getStatus())) continue;

            List<MaintenanceLog> logs = maintenanceLogRepository.findByVehicleId(vehicle.getId());
            BigDecimal currentOdometer = vehicle.getOdometer() != null ? vehicle.getOdometer() : BigDecimal.ZERO;
            BigDecimal lastMaintenanceOdometer = getLastMaintenanceOdometer(logs, currentOdometer);
            long kmSinceService = currentOdometer.subtract(lastMaintenanceOdometer).longValue();

            if (kmSinceService > 5000) {
                result.add(new ActionCenterDTO.VehicleMaintenanceItem(
                    vehicle.getId(),
                    vehicle.getRegistrationNumber(),
                    vehicle.getNameModel(),
                    kmSinceService
                ));
            }
        }

        result.sort(Comparator.comparing(ActionCenterDTO.VehicleMaintenanceItem::getKmSinceService).reversed());
        return result;
    }

    private List<ActionCenterDTO.VehiclePriorityItem> calculateHighestPriorityVehicles(FleetHealthDTO fleetHealth) {
        return fleetHealth.getVehicles().stream()
            .filter(v -> !"Critical".equals(v.getBucket()) && !"Needs Attention".equals(v.getBucket()))
            .sorted(Comparator.comparing(FleetHealthDTO.VehicleHealthItem::getHealthScore))
            .limit(3)
            .map(v -> {
                Vehicle vehicle = vehicleRepository.findById(v.getId()).orElse(null);
                return new ActionCenterDTO.VehiclePriorityItem(
                    v.getId(),
                    v.getRegistrationNumber(),
                    v.getNameModel(),
                    v.getHealthScore(),
                    vehicle != null ? vehicle.getStatus() : "Unknown"
                );
            })
            .collect(Collectors.toList());
    }

    private List<ActionCenterDTO.VehicleUpcomingItem> calculateUpcomingMaintenance(List<Vehicle> vehicles) {
        List<ActionCenterDTO.VehicleUpcomingItem> result = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if ("Retired".equals(vehicle.getStatus())) continue;

            List<MaintenanceLog> logs = maintenanceLogRepository.findByVehicleId(vehicle.getId());
            LocalDateTime oneEightyDaysAgo = LocalDateTime.now().minusDays(180);

            boolean hasRecentMaintenance = logs.stream()
                .anyMatch(log -> log.getCreatedAt() != null && log.getCreatedAt().isAfter(oneEightyDaysAgo));

            if (!hasRecentMaintenance) {
                long daysSinceLastService = getDaysSinceLastService(logs);
                result.add(new ActionCenterDTO.VehicleUpcomingItem(
                    vehicle.getId(),
                    vehicle.getRegistrationNumber(),
                    vehicle.getNameModel(),
                    daysSinceLastService
                ));
            }
        }

        result.sort(Comparator.comparing(ActionCenterDTO.VehicleUpcomingItem::getDaysSinceLastService).reversed());
        return result;
    }

    private BigDecimal getLastMaintenanceOdometer(List<MaintenanceLog> logs, BigDecimal currentOdometer) {
        if (logs.isEmpty()) {
            return BigDecimal.ZERO;
        }

        MaintenanceLog lastLog = logs.stream()
            .filter(log -> log.getCreatedAt() != null)
            .max(Comparator.comparing(MaintenanceLog::getCreatedAt))
            .orElse(null);

        if (lastLog != null) {
            return lastLog.getOdometerAtService() != null ? lastLog.getOdometerAtService() : BigDecimal.ZERO;
        }

        return BigDecimal.ZERO;
    }

    private long getDaysSinceLastService(List<MaintenanceLog> logs) {
        if (logs.isEmpty()) return Long.MAX_VALUE;

        LocalDateTime lastService = logs.stream()
            .filter(log -> log.getCreatedAt() != null)
            .map(MaintenanceLog::getCreatedAt)
            .max(Comparator.naturalOrder())
            .orElse(null);

        if (lastService == null) return Long.MAX_VALUE;

        return java.time.Duration.between(lastService, LocalDateTime.now()).toDays();
    }
}
