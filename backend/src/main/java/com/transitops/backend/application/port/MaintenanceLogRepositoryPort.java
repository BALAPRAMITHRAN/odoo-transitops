package com.transitops.backend.application.port;

import com.transitops.backend.domain.MaintenanceLog;
import java.util.List;
import java.util.Optional;

public interface MaintenanceLogRepositoryPort {

    MaintenanceLog save(MaintenanceLog log);

    Optional<MaintenanceLog> findById(Long id);

    List<MaintenanceLog> findByVehicleId(Long vehicleId);
}
