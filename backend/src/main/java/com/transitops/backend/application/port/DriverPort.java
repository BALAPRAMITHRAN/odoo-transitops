package com.transitops.backend.application.port;

import com.transitops.backend.domain.Driver;
import java.util.Optional;

public interface DriverPort {
    Optional<Driver> findById(Long id);
    Optional<Driver> findByUserEmail(String email);
}
