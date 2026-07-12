package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.Driver;

public class DriverMapper {
    public static Driver toDomain(DriverJpaEntity entity) {
        if (entity == null) return null;
        return new Driver(
                entity.getId(),
                UserMapper.toDomain(entity.getUser()),
                entity.getLicenseNumber(),
                entity.getPhone(),
                entity.getExperienceYears(),
                entity.getStatus()
        );
    }

    public static DriverJpaEntity toJpa(Driver domain) {
        if (domain == null) return null;
        DriverJpaEntity entity = new DriverJpaEntity();
        entity.setId(domain.getId());
        entity.setUser(UserMapper.toJpa(domain.getUser()));
        entity.setLicenseNumber(domain.getLicenseNumber());
        entity.setPhone(domain.getPhone());
        entity.setExperienceYears(domain.getExperienceYears());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}
