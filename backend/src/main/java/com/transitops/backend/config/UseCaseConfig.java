package com.transitops.backend.config;

import com.transitops.backend.application.port.MaintenanceLogRepositoryPort;
import com.transitops.backend.application.port.VehicleRepositoryPort;
import com.transitops.backend.application.usecase.CloseMaintenanceLogUseCase;
import com.transitops.backend.application.usecase.CreateMaintenanceLogUseCase;
import com.transitops.backend.application.usecase.ListVehiclesUseCase;
import com.transitops.backend.application.usecase.RegisterVehicleUseCase;
import com.transitops.backend.application.usecase.UpdateVehicleStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RegisterVehicleUseCase registerVehicleUseCase(VehicleRepositoryPort vehicleRepository) {
        return new RegisterVehicleUseCase(vehicleRepository);
    }

    @Bean
    public UpdateVehicleStatusUseCase updateVehicleStatusUseCase(VehicleRepositoryPort vehicleRepository) {
        return new UpdateVehicleStatusUseCase(vehicleRepository);
    }

    @Bean
    public CreateMaintenanceLogUseCase createMaintenanceLogUseCase(MaintenanceLogRepositoryPort maintenanceLogRepository,
                                                                    VehicleRepositoryPort vehicleRepository) {
        return new CreateMaintenanceLogUseCase(maintenanceLogRepository, vehicleRepository);
    }

    @Bean
    public CloseMaintenanceLogUseCase closeMaintenanceLogUseCase(MaintenanceLogRepositoryPort maintenanceLogRepository,
                                                                  VehicleRepositoryPort vehicleRepository) {
        return new CloseMaintenanceLogUseCase(maintenanceLogRepository, vehicleRepository);
    }

    @Bean
    public ListVehiclesUseCase listVehiclesUseCase(VehicleRepositoryPort vehicleRepository,
                                                    MaintenanceLogRepositoryPort maintenanceLogRepository) {
        return new ListVehiclesUseCase(vehicleRepository, maintenanceLogRepository);
    }
}
