package com.transitops.backend.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MaintenanceLogSpringDataRepository extends JpaRepository<MaintenanceLogJpaEntity, Long> {

    List<MaintenanceLogJpaEntity> findByVehicleId(Long vehicleId);
}
