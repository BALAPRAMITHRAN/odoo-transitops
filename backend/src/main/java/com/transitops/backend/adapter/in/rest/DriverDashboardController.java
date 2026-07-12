package com.transitops.backend.adapter.in.rest;

import com.transitops.backend.adapter.in.rest.dto.*;
import com.transitops.backend.application.usecase.DriverDashboardUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DriverDashboardController {

    private final DriverDashboardUseCase useCase;

    public DriverDashboardController(DriverDashboardUseCase useCase) {
        this.useCase = useCase;
    }

    private Long resolveDriverId(String headerDriverId, Long paramDriverId) {
        if (headerDriverId != null && !headerDriverId.isBlank()) {
            try {
                return Long.parseLong(headerDriverId);
            } catch (NumberFormatException e) {
                // fall through
            }
        }
        if (paramDriverId != null) {
            return paramDriverId;
        }
        return 1L; // Fallback to pre-seeded driver (Amit Kumar)
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DriverDashboardResponse> getDashboard(
            @RequestHeader(value = "X-Driver-Id", required = false) String headerDriverId,
            @RequestParam(value = "driverId", required = false) Long paramDriverId) {
        Long driverId = resolveDriverId(headerDriverId, paramDriverId);
        return ResponseEntity.ok(useCase.getDashboard(driverId));
    }

    @GetMapping("/my-trips")
    public ResponseEntity<List<DriverTripResponse>> getMyTrips(
            @RequestHeader(value = "X-Driver-Id", required = false) String headerDriverId,
            @RequestParam(value = "driverId", required = false) Long paramDriverId) {
        Long driverId = resolveDriverId(headerDriverId, paramDriverId);
        return ResponseEntity.ok(useCase.getMyTrips(driverId));
    }

    @GetMapping("/active-deliveries")
    public ResponseEntity<List<ActiveDeliveryResponse>> getActiveDeliveries(
            @RequestHeader(value = "X-Driver-Id", required = false) String headerDriverId,
            @RequestParam(value = "driverId", required = false) Long paramDriverId) {
        Long driverId = resolveDriverId(headerDriverId, paramDriverId);
        return ResponseEntity.ok(useCase.getActiveDeliveries(driverId));
    }

    @GetMapping("/assigned-vehicle")
    public ResponseEntity<DriverVehicleResponse> getAssignedVehicle(
            @RequestHeader(value = "X-Driver-Id", required = false) String headerDriverId,
            @RequestParam(value = "driverId", required = false) Long paramDriverId) {
        Long driverId = resolveDriverId(headerDriverId, paramDriverId);
        return ResponseEntity.ok(useCase.getAssignedVehicle(driverId));
    }

    @GetMapping("/profile")
    public ResponseEntity<DriverProfileResponse> getDriverProfile(
            @RequestHeader(value = "X-Driver-Id", required = false) String headerDriverId,
            @RequestParam(value = "driverId", required = false) Long paramDriverId) {
        Long driverId = resolveDriverId(headerDriverId, paramDriverId);
        return ResponseEntity.ok(useCase.getDriverProfile(driverId));
    }
}
