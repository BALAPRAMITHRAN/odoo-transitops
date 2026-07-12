package com.transitops.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trip {
    private Long id;
    private Vehicle vehicle;
    private Driver driver;
    private String startLocation;
    private String destination;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal distanceKm;
    private String tripStatus;

    public Trip() {}

    public Trip(Long id, Vehicle vehicle, Driver driver, String startLocation, String destination,
                LocalDateTime startTime, LocalDateTime endTime, BigDecimal distanceKm, String tripStatus) {
        this.id = id;
        this.vehicle = vehicle;
        this.driver = driver;
        this.startLocation = startLocation;
        this.destination = destination;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distanceKm = distanceKm;
        this.tripStatus = tripStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public BigDecimal getDistanceKm() { return distanceKm; }
    public void setDistanceKm(BigDecimal distanceKm) { this.distanceKm = distanceKm; }

    public String getTripStatus() { return tripStatus; }
    public void setTripStatus(String tripStatus) { this.tripStatus = tripStatus; }
}
