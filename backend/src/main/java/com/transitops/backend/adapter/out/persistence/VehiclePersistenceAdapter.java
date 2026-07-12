package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehiclePersistenceAdapter implements VehicleRepositoryPort {

    private final VehicleSpringDataRepository jpaRepo;

    public VehiclePersistenceAdapter(VehicleSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleJpaEntity saved = jpaRepo.save(VehicleMapper.toJpa(vehicle));
        return VehicleMapper.toDomain(saved);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return jpaRepo.findById(id).map(VehicleMapper::toDomain);
    }

    @Override
    public Optional<Vehicle> findByRegistrationNumber(String registrationNumber) {
        return jpaRepo.findByRegistrationNumber(registrationNumber).map(VehicleMapper::toDomain);
    }

    @Override
    public List<Vehicle> findAll() {
        return jpaRepo.findAll().stream().map(VehicleMapper::toDomain).toList();
    }

    @Override
    public List<Vehicle> findByStatus(String status) {
        return jpaRepo.findByStatus(status).stream().map(VehicleMapper::toDomain).toList();
    }

    @Override
    public long countByStatus(String status) {
        return jpaRepo.countByStatus(status);
    }

    @Override
    public long countAll() {
        return jpaRepo.count();
    }
}
