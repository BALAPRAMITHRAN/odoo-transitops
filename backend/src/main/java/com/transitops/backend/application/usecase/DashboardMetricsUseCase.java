package com.transitops.backend.application.usecase;

import com.transitops.backend.adapter.in.rest.dto.DashboardMetricsDTO;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardMetricsUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;

    public DashboardMetricsUseCase(VehicleRepositoryPort vehicleRepository,
                                   MaintenanceLogRepositoryPort maintenanceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    public DashboardMetricsDTO execute() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        
        DashboardMetricsDTO.FleetReadinessDTO fleetReadiness = calculateFleetReadiness(allVehicles);
        DashboardMetricsDTO.VehicleHealthSummaryDTO healthSummary = calculateVehicleHealthSummary(allVehicles);
        DashboardMetricsDTO.OperationalInsightsDTO insights = generateOperationalInsights(allVehicles, fleetReadiness, healthSummary);
        DashboardMetricsDTO.ActionCenterDTO actionCenter = calculateActionCenter(allVehicles);

        return new DashboardMetricsDTO(fleetReadiness, healthSummary, insights, actionCenter);
    }

    private DashboardMetricsDTO.FleetReadinessDTO calculateFleetReadiness(List<Vehicle> vehicles) {
        int total = vehicles.size();
        int available = (int) vehicles.stream().filter(v -> "Available".equals(v.getStatus())).count();
        int onTrip = (int) vehicles.stream().filter(v -> "On Trip".equals(v.getStatus())).count();
        int inMaintenance = (int) vehicles.stream().filter(v -> "In Shop".equals(v.getStatus())).count();
        int retired = (int) vehicles.stream().filter(v -> "Retired".equals(v.getStatus())).count();

        int operationalFleet = total - retired;
        double operationalAvailability = operationalFleet > 0 
            ? ((available + onTrip) / (double) operationalFleet) * 100 
            : 0.0;
        
        double fleetReadinessScore = operationalFleet > 0
            ? ((available + onTrip) / (double) operationalFleet) * 100
            : 0.0;

        return new DashboardMetricsDTO.FleetReadinessDTO(
            total, available, onTrip, inMaintenance, retired,
            Math.round(fleetReadinessScore * 10.0) / 10.0,
            Math.round(operationalAvailability * 10.0) / 10.0
        );
    }

    private DashboardMetricsDTO.VehicleHealthSummaryDTO calculateVehicleHealthSummary(List<Vehicle> vehicles) {
        int available = (int) vehicles.stream().filter(v -> "Available".equals(v.getStatus())).count();
        
        int good = (int) vehicles.stream()
            .filter(v -> v.getHealthScore() != null && v.getHealthScore() >= 80)
            .count();
        
        int needsService = (int) vehicles.stream()
            .filter(v -> v.getHealthScore() != null && v.getHealthScore() >= 50 && v.getHealthScore() < 80)
            .count();
        
        int critical = (int) vehicles.stream()
            .filter(v -> v.getHealthScore() != null && v.getHealthScore() < 50)
            .count();

        double avgHealth = vehicles.stream()
            .filter(v -> v.getHealthScore() != null)
            .mapToInt(Vehicle::getHealthScore)
            .average()
            .orElse(0.0);

        return new DashboardMetricsDTO.VehicleHealthSummaryDTO(
            available, good, needsService, critical,
            Math.round(avgHealth * 10.0) / 10.0
        );
    }

    private DashboardMetricsDTO.OperationalInsightsDTO generateOperationalInsights(
            List<Vehicle> vehicles, 
            DashboardMetricsDTO.FleetReadinessDTO fleetReadiness,
            DashboardMetricsDTO.VehicleHealthSummaryDTO healthSummary) {
        
        List<String> insights = new ArrayList<>();

        if (fleetReadiness.getFleetReadinessScore() >= 80) {
            insights.add("Fleet readiness remains healthy.");
        } else if (fleetReadiness.getFleetReadinessScore() >= 60) {
            insights.add("Fleet readiness requires attention.");
        } else {
            insights.add("Fleet readiness is critical.");
        }

        long vehiclesNeedingMaintenance = vehicles.stream()
            .filter(v -> "In Shop".equals(v.getStatus()))
            .count();
        
        if (vehiclesNeedingMaintenance > 0) {
            insights.add(vehiclesNeedingMaintenance + " vehicle(s) require maintenance.");
        }

        if (fleetReadiness.getTotalFleet() > 0) {
            double retiredPercentage = (fleetReadiness.getRetired() / (double) fleetReadiness.getTotalFleet()) * 100;
            insights.add("Retired vehicles represent " + Math.round(retiredPercentage) + "% of the fleet.");
        }

        if (healthSummary.getCritical() > 0) {
            insights.add(healthSummary.getCritical() + " vehicle(s) in critical health condition.");
        }

        if (fleetReadiness.getOperationalAvailability() >= 75) {
            insights.add("No operational bottlenecks detected.");
        } else {
            insights.add("Operational availability below optimal threshold.");
        }

        return new DashboardMetricsDTO.OperationalInsightsDTO(insights);
    }

    private DashboardMetricsDTO.ActionCenterDTO calculateActionCenter(List<Vehicle> vehicles) {
        List<Vehicle> inShopVehicles = vehicles.stream()
            .filter(v -> "In Shop".equals(v.getStatus()))
            .collect(Collectors.toList());

        List<DashboardMetricsDTO.VehicleActionItem> requiringMaintenance = inShopVehicles.stream()
            .map(this::toVehicleActionItem)
            .collect(Collectors.toList());

        List<DashboardMetricsDTO.VehicleActionItem> highestPriority = vehicles.stream()
            .filter(v -> "High".equals(v.getPriority()) || "Critical".equals(v.getPriority()))
            .filter(v -> !"Retired".equals(v.getStatus()))
            .sorted(Comparator.comparing(Vehicle::getHealthScore))
            .limit(5)
            .map(this::toVehicleActionItem)
            .collect(Collectors.toList());

        List<DashboardMetricsDTO.VehicleActionItem> upcomingMaintenance = vehicles.stream()
            .filter(v -> v.getHealthScore() != null && v.getHealthScore() < 70)
            .filter(v -> !"In Shop".equals(v.getStatus()) && !"Retired".equals(v.getStatus()))
            .sorted(Comparator.comparing(Vehicle::getHealthScore))
            .limit(5)
            .map(this::toVehicleActionItem)
            .collect(Collectors.toList());

        return new DashboardMetricsDTO.ActionCenterDTO(
            requiringMaintenance,
            highestPriority,
            upcomingMaintenance
        );
    }

    private DashboardMetricsDTO.VehicleActionItem toVehicleActionItem(Vehicle vehicle) {
        return new DashboardMetricsDTO.VehicleActionItem(
            vehicle.getId(),
            vehicle.getRegistrationNumber(),
            vehicle.getNameModel(),
            vehicle.getStatus(),
            vehicle.getHealthScore(),
            vehicle.getPriority()
        );
    }
}
