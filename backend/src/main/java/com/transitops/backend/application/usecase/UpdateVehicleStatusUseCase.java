package com.transitops.backend.application.usecase;

import com.transitops.backend.application.exception.VehicleNotFoundException;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.Vehicle;

public class UpdateVehicleStatusUseCase {

    private final VehicleRepositoryPort vehicleRepository;

    public UpdateVehicleStatusUseCase(VehicleRepositoryPort vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle execute(Long vehicleId, Vehicle updates) {
        Vehicle existing = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        if (updates.getNameModel() != null) existing.setNameModel(updates.getNameModel());
        if (updates.getVehicleType() != null) existing.setVehicleType(updates.getVehicleType());
        if (updates.getMaxLoadCapacity() != null) existing.setMaxLoadCapacity(updates.getMaxLoadCapacity());
        if (updates.getOdometer() != null) existing.setOdometer(updates.getOdometer());
        if (updates.getAcquisitionCost() != null) existing.setAcquisitionCost(updates.getAcquisitionCost());
        if (updates.getStatus() != null) existing.setStatus(updates.getStatus());
        if (updates.getRegion() != null) existing.setRegion(updates.getRegion());

        return vehicleRepository.save(existing);
    }
}
