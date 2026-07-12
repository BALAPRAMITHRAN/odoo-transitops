package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.Vehicle;

public class VehicleMapper {

    public static Vehicle toDomain(VehicleJpaEntity entity) {
        return new Vehicle(
                entity.getId(),
                entity.getRegistrationNumber(),
                entity.getNameModel(),
                entity.getVehicleType(),
                entity.getMaxLoadCapacity(),
                entity.getOdometer(),
                entity.getAcquisitionCost(),
                entity.getStatus(),
                entity.getRegion(),
                entity.getHealthScore(),
                entity.getPriority()
        );
    }

    public static VehicleJpaEntity toJpa(Vehicle domain) {
        VehicleJpaEntity entity = new VehicleJpaEntity();
        entity.setId(domain.getId());
        entity.setRegistrationNumber(domain.getRegistrationNumber());
        entity.setNameModel(domain.getNameModel());
        entity.setVehicleType(domain.getVehicleType());
        entity.setMaxLoadCapacity(domain.getMaxLoadCapacity());
        entity.setOdometer(domain.getOdometer());
        entity.setAcquisitionCost(domain.getAcquisitionCost());
        entity.setStatus(domain.getStatus());
        entity.setRegion(domain.getRegion());
        entity.setHealthScore(domain.getHealthScore());
        entity.setPriority(domain.getPriority());
        return entity;
    }
}
