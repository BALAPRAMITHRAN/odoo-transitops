package com.transitops.backend.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TripSpringDataRepository extends JpaRepository<TripJpaEntity, Long> {
    List<TripJpaEntity> findByDriverId(Long driverId);

    @Query("SELECT t FROM TripJpaEntity t WHERE t.driver.id = :driverId AND (t.tripStatus = 'DISPATCHED' OR t.tripStatus = 'ONGOING')")
    List<TripJpaEntity> findActiveDeliveriesByDriverId(@Param("driverId") Long driverId);
}
