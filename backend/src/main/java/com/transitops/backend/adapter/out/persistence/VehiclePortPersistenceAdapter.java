package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.application.port.VehiclePort;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class VehiclePortPersistenceAdapter implements VehiclePort {

    private final VehicleSpringDataRepository jpaRepo;

    public VehiclePortPersistenceAdapter(VehicleSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return jpaRepo.findById(id).map(VehicleMapper::toDomain);
    }
}
