package com.transitops.backend.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleSpringDataRepository extends JpaRepository<VehicleJpaEntity, Long> {

    Optional<VehicleJpaEntity> findByRegistrationNumber(String registrationNumber);

    List<VehicleJpaEntity> findByStatus(String status);

    long countByStatus(String status);
}
