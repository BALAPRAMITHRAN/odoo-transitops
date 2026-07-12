package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.Trip;

public class TripMapper {
    public static Trip toDomain(TripJpaEntity entity) {
        if (entity == null) return null;
        return new Trip(
                entity.getId(),
                VehicleMapper.toDomain(entity.getVehicle()),
                DriverMapper.toDomain(entity.getDriver()),
                entity.getStartLocation(),
                entity.getDestination(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getDistanceKm(),
                entity.getTripStatus()
        );
    }

    public static TripJpaEntity toJpa(Trip domain) {
        if (domain == null) return null;
        TripJpaEntity entity = new TripJpaEntity();
        entity.setId(domain.getId());
        entity.setVehicle(VehicleMapper.toJpa(domain.getVehicle()));
        entity.setDriver(DriverMapper.toJpa(domain.getDriver()));
        entity.setStartLocation(domain.getStartLocation());
        entity.setDestination(domain.getDestination());
        entity.setStartTime(domain.getStartTime());
        entity.setEndTime(domain.getEndTime());
        entity.setDistanceKm(domain.getDistanceKm());
        entity.setTripStatus(domain.getTripStatus());
        return entity;
    }
}
