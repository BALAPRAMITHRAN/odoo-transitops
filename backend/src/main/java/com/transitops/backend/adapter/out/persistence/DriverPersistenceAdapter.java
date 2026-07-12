package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.application.port.DriverPort;
import com.transitops.backend.domain.Driver;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class DriverPersistenceAdapter implements DriverPort {

    private final DriverSpringDataRepository jpaRepo;

    public DriverPersistenceAdapter(DriverSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Optional<Driver> findById(Long id) {
        return jpaRepo.findById(id).map(DriverMapper::toDomain);
    }

    @Override
    public Optional<Driver> findByUserEmail(String email) {
        return jpaRepo.findByUserEmail(email).map(DriverMapper::toDomain);
    }
}
