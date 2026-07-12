package com.transitops.backend.application.port;

import com.transitops.backend.domain.Vehicle;
import java.util.Optional;

public interface VehiclePort {
    Optional<Vehicle> findById(Long id);
}
