package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.domain.User;

public class UserMapper {
    public static User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                RoleMapper.toDomain(entity.getRole()),
                entity.getCreatedAt()
        );
    }

    public static UserJpaEntity toJpa(User domain) {
        if (domain == null) return null;
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId());
        entity.setFullName(domain.getFullName());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setRole(RoleMapper.toJpa(domain.getRole()));
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
