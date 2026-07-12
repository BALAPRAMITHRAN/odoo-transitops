package com.transitops.backend.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverSpringDataRepository extends JpaRepository<DriverJpaEntity, Long> {
    Optional<DriverJpaEntity> findByUserEmail(String email);
}
