package com.transitops.backend.application.usecase;

import com.transitops.backend.adapter.in.rest.dto.CostOfOwnershipDTO;
import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostOfOwnershipUseCase {

    private final VehicleRepositoryPort vehicleRepository;
    private final MaintenanceLogRepositoryPort maintenanceLogRepository;

    public CostOfOwnershipUseCase(VehicleRepositoryPort vehicleRepository,
                                   MaintenanceLogRepositoryPort maintenanceLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.maintenanceLogRepository = maintenanceLogRepository;
    }

    public CostOfOwnershipDTO execute() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        List<CostOfOwnershipDTO.VehicleCostItem> vehicleItems = new ArrayList<>();

        for (Vehicle vehicle : allVehicles) {
            List<MaintenanceLog> logs = maintenanceLogRepository.findByVehicleId(vehicle.getId());

            double totalMaintenanceCost = logs.stream()
                .filter(log -> log.getCost() != null)
                .mapToDouble(log -> log.getCost().doubleValue())
                .sum();

            BigDecimal acquisitionCost = vehicle.getAcquisitionCost() != null ? vehicle.getAcquisitionCost() : BigDecimal.ONE;
            double ownershipRatio = totalMaintenanceCost / acquisitionCost.doubleValue();
            boolean considerRetirement = ownershipRatio > 0.4;

            vehicleItems.add(new CostOfOwnershipDTO.VehicleCostItem(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getNameModel(),
                totalMaintenanceCost,
                ownershipRatio,
                considerRetirement
            ));
        }

        vehicleItems.sort(Comparator.comparing(CostOfOwnershipDTO.VehicleCostItem::getOwnershipRatio).reversed());

        return new CostOfOwnershipDTO(vehicleItems.stream().limit(5).collect(Collectors.toList()));
    }
}
