package com.transitops.backend.application.usecase;

import com.transitops.backend.adapter.in.rest.dto.OperationalInsightsDTO;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RepeatMaintenanceInsightUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;

    public RepeatMaintenanceInsightUseCase(VehicleRepositoryPort vehicleRepository,
                                            MaintenanceLogRepositoryPort maintenanceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    public OperationalInsightsDTO execute() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        List<String> insights = new ArrayList<>();

        for (Vehicle vehicle : allVehicles) {
            List<MaintenanceLog> logs = maintenanceLogRepository.findByVehicleId(vehicle.getId());

            if (logs.size() >= 3) {
                insights.add(String.format("%s has been serviced %d times in the last %d days — consider inspection or retirement.",
                    vehicle.getRegistrationNumber(),
                    logs.size(),
                    getDaySpan(logs)
                ));
            } else {
                LocalDateTime sixtyDaysAgo = LocalDateTime.now().minusDays(60);
                long recentLogsCount = logs.stream()
                    .filter(log -> log.getCreatedAt() != null)
                    .filter(log -> log.getCreatedAt().isAfter(sixtyDaysAgo))
                    .count();

                if (recentLogsCount >= 2) {
                    insights.add(String.format("%s has been serviced %d times in the last %d days — consider inspection or retirement.",
                        vehicle.getRegistrationNumber(),
                        (int) recentLogsCount,
                        60
                    ));
                }
            }
        }

        insights.sort(Comparator.comparing(String::length).reversed());

        return new OperationalInsightsDTO(insights.stream().limit(5).collect(Collectors.toList()));
    }

    private int getDaySpan(List<MaintenanceLog> logs) {
        if (logs.isEmpty()) return 0;

        LocalDateTime earliest = logs.stream()
            .filter(log -> log.getCreatedAt() != null)
            .map(MaintenanceLog::getCreatedAt)
            .min(Comparator.naturalOrder())
            .orElse(null);

        if (earliest == null) return 0;

        long days = java.time.Duration.between(earliest, LocalDateTime.now()).toDays();
        return (int) Math.max(1, days);
    }
}
