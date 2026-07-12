package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.MaintenanceLog;

public class MaintenanceLogMapper {

    public static MaintenanceLog toDomain(MaintenanceLogJpaEntity entity) {
        return new MaintenanceLog(
                entity.getId(),
                entity.getVehicleId(),
                entity.getDescription(),
                entity.getCost(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getClosedAt(),
                entity.getOdometerAtService()
        );
    }

    public static MaintenanceLogJpaEntity toJpa(MaintenanceLog domain) {
        MaintenanceLogJpaEntity entity = new MaintenanceLogJpaEntity();
        entity.setId(domain.getId());
        entity.setVehicleId(domain.getVehicleId());
        entity.setDescription(domain.getDescription());
        entity.setCost(domain.getCost());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setClosedAt(domain.getClosedAt());
        entity.setOdometerAtService(domain.getOdometerAtService());
        return entity;
    }
}
