package com.transitops.backend.application.service;

import com.transitops.backend.adapter.in.rest.dto.*;
import com.transitops.backend.application.port.*;
import com.transitops.backend.application.usecase.DriverDashboardUseCase;
import com.transitops.backend.domain.Driver;
import com.transitops.backend.domain.Trip;
import com.transitops.backend.domain.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverDashboardService implements DriverDashboardUseCase {

    private final TripPort tripPort;
    private final DriverPort driverPort;
    private final VehiclePort vehiclePort;

    public DriverDashboardService(TripPort tripPort, DriverPort driverPort, VehiclePort vehiclePort) {
        this.tripPort = tripPort;
        this.driverPort = driverPort;
        this.vehiclePort = vehiclePort;
    }

    @Override
    public DriverDashboardResponse getDashboard(Long driverId) {
        Driver driver = driverPort.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));

        List<Trip> myTrips = tripPort.findByDriverId(driverId);
        List<Trip> activeDeliveries = tripPort.findActiveDeliveriesByDriverId(driverId);

        int assignedCount = myTrips.size();
        int activeCount = activeDeliveries.size();
        int completedCount = (int) myTrips.stream()
                .filter(t -> "COMPLETED".equalsIgnoreCase(t.getTripStatus()))
                .count();

        String assignedVehicleNo = "N/A";
        if (!activeDeliveries.isEmpty() && activeDeliveries.get(0).getVehicle() != null) {
            assignedVehicleNo = activeDeliveries.get(0).getVehicle().getRegistrationNumber();
        } else if (!myTrips.isEmpty() && myTrips.get(0).getVehicle() != null) {
            assignedVehicleNo = myTrips.get(0).getVehicle().getRegistrationNumber();
        }

        // Recent Trips (last 5)
        List<DriverTripResponse> recentTrips = myTrips.stream()
                .limit(5)
                .map(this::mapToTripResponse)
                .toList();

        // Upcoming Deliveries (DRAFT or PLANNED status)
        List<DriverTripResponse> upcomingDeliveries = myTrips.stream()
                .filter(t -> "DRAFT".equalsIgnoreCase(t.getTripStatus()) || "PLANNED".equalsIgnoreCase(t.getTripStatus()))
                .map(this::mapToTripResponse)
                .toList();

        // Mock recent activity feed
        List<String> recentActivity = new ArrayList<>();
        if (activeCount > 0) {
            recentActivity.add("Active transit ongoing for vehicle: " + assignedVehicleNo);
        }
        recentActivity.add("Checked in for shift duty.");
        recentActivity.add("Assigned safety rating reviewed: 98/100");

        DriverDashboardResponse response = new DriverDashboardResponse();
        response.setAssignedTrips(assignedCount);
        response.setActiveDeliveries(activeCount);
        response.setCompletedDeliveries(completedCount);
        response.setAssignedVehicleNumber(assignedVehicleNo);
        response.setDriverStatus(driver.getStatus());
        response.setRecentTrips(recentTrips);
        response.setUpcomingDeliveries(upcomingDeliveries);
        response.setRecentActivity(recentActivity);

        return response;
    }

    @Override
    public List<DriverTripResponse> getMyTrips(Long driverId) {
        return tripPort.findByDriverId(driverId).stream()
                .map(this::mapToTripResponse)
                .toList();
    }

    @Override
    public List<ActiveDeliveryResponse> getActiveDeliveries(Long driverId) {
        return tripPort.findActiveDeliveriesByDriverId(driverId).stream()
                .map(this::mapToActiveResponse)
                .toList();
    }

    @Override
    public DriverVehicleResponse getAssignedVehicle(Long driverId) {
        List<Trip> activeTrips = tripPort.findActiveDeliveriesByDriverId(driverId);
        Vehicle vehicle = null;
        if (!activeTrips.isEmpty()) {
            vehicle = activeTrips.get(0).getVehicle();
        } else {
            List<Trip> allTrips = tripPort.findByDriverId(driverId);
            if (!allTrips.isEmpty()) {
                vehicle = allTrips.get(0).getVehicle();
            }
        }

        if (vehicle == null) {
            throw new RuntimeException("No vehicle assigned to driver ID: " + driverId);
        }

        DriverVehicleResponse response = new DriverVehicleResponse();
        response.setVehicleNumber(vehicle.getRegistrationNumber());
        response.setVehicleModel(vehicle.getNameModel());
        response.setVehicleType(vehicle.getVehicleType());
        response.setRegistrationNumber(vehicle.getRegistrationNumber());
        response.setMaxCapacity(vehicle.getMaxLoadCapacity() != null ? vehicle.getMaxLoadCapacity().doubleValue() : 5000.0);
        response.setCurrentOdometer(vehicle.getOdometer() != null ? vehicle.getOdometer().doubleValue() : 0.0);
        response.setVehicleStatus(vehicle.getStatus());
        response.setLastMaintenanceDate("2026-06-25");
        response.setFuelEfficiency("8.2 km/l");

        return response;
    }

    @Override
    public DriverProfileResponse getDriverProfile(Long driverId) {
        Driver driver = driverPort.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));

        DriverProfileResponse response = new DriverProfileResponse();
        if (driver.getUser() != null) {
            response.setDriverName(driver.getUser().getFullName());
            response.setEmail(driver.getUser().getEmail());
        } else {
            response.setDriverName("Driver #" + driver.getId());
            response.setEmail("driver" + driver.getId() + "@transitops.com");
        }
        response.setEmployeeId("EMP-" + driver.getId());
        response.setPhoneNumber(driver.getPhone() != null ? driver.getPhone() : "N/A");
        response.setLicenseNumber(driver.getLicenseNumber());
        response.setLicenseCategory("Class A Commercial");
        response.setLicenseExpiry("2031-12-31");
        response.setSafetyScore(98);
        response.setCurrentStatus(driver.getStatus());

        return response;
    }

    private DriverTripResponse mapToTripResponse(Trip trip) {
        DriverTripResponse response = new DriverTripResponse();
        response.setId(trip.getId());
        response.setSource(trip.getStartLocation());
        response.setDestination(trip.getDestination());
        if (trip.getVehicle() != null) {
            response.setVehicleName(trip.getVehicle().getNameModel());
            response.setVehicleNumber(trip.getVehicle().getRegistrationNumber());
            // Calculate a mock cargo weight: 85% of vehicle capacity or 4500kg default
            double maxCap = trip.getVehicle().getMaxLoadCapacity() != null ?
                    trip.getVehicle().getMaxLoadCapacity().doubleValue() : 5000.0;
            response.setCargoWeight(maxCap * 0.85);
        } else {
            response.setVehicleName("N/A");
            response.setVehicleNumber("N/A");
            response.setCargoWeight(2500.0);
        }
        response.setPlannedDistance(trip.getDistanceKm() != null ? trip.getDistanceKm().doubleValue() : 0.0);
        response.setDispatchTime(trip.getStartTime());
        response.setExpectedArrival(trip.getEndTime());
        response.setStatus(trip.getTripStatus());
        return response;
    }

    private ActiveDeliveryResponse mapToActiveResponse(Trip trip) {
        ActiveDeliveryResponse response = new ActiveDeliveryResponse();
        response.setId(trip.getId());
        if (trip.getVehicle() != null) {
            response.setVehicleName(trip.getVehicle().getNameModel());
            double maxCap = trip.getVehicle().getMaxLoadCapacity() != null ?
                    trip.getVehicle().getMaxLoadCapacity().doubleValue() : 5000.0;
            response.setCargoWeight(maxCap * 0.85);
        } else {
            response.setVehicleName("N/A");
            response.setCargoWeight(2500.0);
        }
        response.setSource(trip.getStartLocation());
        response.setDestination(trip.getDestination());
        response.setDistance(trip.getDistanceKm() != null ? trip.getDistanceKm().doubleValue() : 0.0);
        response.setDispatchTime(trip.getStartTime());
        response.setExpectedArrival(trip.getEndTime());
        response.setCurrentStatus(trip.getTripStatus());
        return response;
    }
}
