package com.transitops.backend.application.usecase;

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
public class VehicleHealthScoreUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;

    public VehicleHealthScoreUseCase(VehicleRepositoryPort vehicleRepository,
                                     MaintenanceLogRepositoryPort maintenanceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    public FleetHealthDTO execute() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        List<FleetHealthDTO.VehicleHealthItem> vehicleItems = new ArrayList<>();
        
        int excellent = 0;
        int good = 0;
        int needsAttention = 0;
        int critical = 0;

        for (Vehicle vehicle : allVehicles) {
            if ("Retired".equals(vehicle.getStatus())) {
                continue;
            }

            int healthScore = calculateHealthScore(vehicle);
            String bucket = getBucket(healthScore);

            vehicleItems.add(new FleetHealthDTO.VehicleHealthItem(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getNameModel(),
                healthScore,
                bucket
            ));

            switch (bucket) {
                case "Excellent": excellent++; break;
                case "Good": good++; break;
                case "Needs Attention": needsAttention++; break;
                case "Critical": critical++; break;
            }
        }

        return new FleetHealthDTO(excellent, good, needsAttention, critical, vehicleItems);
    }

    private int calculateHealthScore(Vehicle vehicle) {
        int score = 100;
        List<MaintenanceLog> logs = maintenanceLogRepository.findByVehicleId(vehicle.getId());

        BigDecimal currentOdometer = vehicle.getOdometer() != null ? vehicle.getOdometer() : BigDecimal.ZERO;
        BigDecimal lastMaintenanceOdometer = getLastMaintenanceOdometer(logs, currentOdometer);
        long odometerSinceLastMaintenance = currentOdometer.subtract(lastMaintenanceOdometer).longValue();

        score -= Math.min(30, (odometerSinceLastMaintenance / 1000) * 2);

        boolean hasActiveLog = logs.stream().anyMatch(log -> "ACTIVE".equals(log.getStatus()));
        if (hasActiveLog) {
            score -= 20;
        }

        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        long recentLogsCount = logs.stream()
            .filter(log -> log.getCreatedAt() != null)
            .filter(log -> log.getCreatedAt().isAfter(ninetyDaysAgo))
            .count();

        if (recentLogsCount > 1) {
            score -= (recentLogsCount - 1) * 10;
        }

        return Math.max(0, Math.min(100, score));
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

    private String getBucket(int score) {
        if (score >= 80) return "Excellent";
        if (score >= 60) return "Good";
        if (score >= 40) return "Needs Attention";
        return "Critical";
    }
}
