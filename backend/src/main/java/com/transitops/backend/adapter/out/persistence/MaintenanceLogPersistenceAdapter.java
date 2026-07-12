package com.transitops.backend.adapter.out.persistence;

import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.domain.MaintenanceLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MaintenanceLogPersistenceAdapter implements MaintenanceLogRepositoryPort {

    private final MaintenanceLogSpringDataRepository jpaRepo;

    public MaintenanceLogPersistenceAdapter(MaintenanceLogSpringDataRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public MaintenanceLog save(MaintenanceLog log) {
        MaintenanceLogJpaEntity saved = jpaRepo.save(MaintenanceLogMapper.toJpa(log));
        return MaintenanceLogMapper.toDomain(saved);
    }

    @Override
    public Optional<MaintenanceLog> findById(Long id) {
        return jpaRepo.findById(id).map(MaintenanceLogMapper::toDomain);
    }

    @Override
    public List<MaintenanceLog> findByVehicleId(Long vehicleId) {
        return jpaRepo.findByVehicleId(vehicleId).stream().map(MaintenanceLogMapper::toDomain).toList();
    }
}
