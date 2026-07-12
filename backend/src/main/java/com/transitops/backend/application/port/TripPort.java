package com.transitops.backend.application.port;

import com.transitops.backend.domain.Trip;
import java.util.List;
import java.util.Optional;

public interface TripPort {
    Optional<Trip> findById(Long id);
    List<Trip> findAll();
    List<Trip> findByDriverId(Long driverId);
    List<Trip> findActiveDeliveriesByDriverId(Long driverId);
}
