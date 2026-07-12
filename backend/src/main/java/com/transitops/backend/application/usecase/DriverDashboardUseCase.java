package com.transitops.backend.application.usecase;

import com.transitops.backend.adapter.in.rest.dto.*;
import java.util.List;

public interface DriverDashboardUseCase {
    DriverDashboardResponse getDashboard(Long driverId);
    List<DriverTripResponse> getMyTrips(Long driverId);
    List<ActiveDeliveryResponse> getActiveDeliveries(Long driverId);
    DriverVehicleResponse getAssignedVehicle(Long driverId);
    DriverProfileResponse getDriverProfile(Long driverId);
}
