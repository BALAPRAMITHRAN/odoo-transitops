package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.Role;

public class RoleMapper {
    public static Role toDomain(RoleJpaEntity entity) {
        if (entity == null) return null;
        return new Role(entity.getId(), entity.getRoleName(), entity.getDescription());
    }

    public static RoleJpaEntity toJpa(Role domain) {
        if (domain == null) return null;
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.setId(domain.getId());
        entity.setRoleName(domain.getRoleName());
        entity.setDescription(domain.getDescription());
        return entity;
    }
}
