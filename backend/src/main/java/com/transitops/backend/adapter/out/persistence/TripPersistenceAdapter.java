package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.application.port.TripPort;
import com.transitops.backend.domain.Trip;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TripPersistenceAdapter implements TripPort {

    private final TripSpringDataRepository jpaRepo;

    public TripPersistenceAdapter(TripSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Optional<Trip> findById(Long id) {
        return jpaRepo.findById(id).map(TripMapper::toDomain);
    }

    @Override
    public List<Trip> findAll() {
        return jpaRepo.findAll().stream().map(TripMapper::toDomain).toList();
    }

    @Override
    public List<Trip> findByDriverId(Long driverId) {
        return jpaRepo.findByDriverId(driverId).stream().map(TripMapper::toDomain).toList();
    }

    @Override
    public List<Trip> findActiveDeliveriesByDriverId(Long driverId) {
        return jpaRepo.findActiveDeliveriesByDriverId(driverId).stream().map(TripMapper::toDomain).toList();
    }
}
