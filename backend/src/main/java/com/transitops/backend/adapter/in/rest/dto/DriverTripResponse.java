package com.transitops.backend.adapter.in.rest.dto;

import java.time.LocalDateTime;

public class DriverTripResponse {
    private Long id;
    private String source;
    private String destination;
    private String vehicleName;
    private String vehicleNumber;
    private double cargoWeight;
    private double plannedDistance;
    private LocalDateTime dispatchTime;
    private LocalDateTime expectedArrival;
    private String status;

    public DriverTripResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public double getCargoWeight() { return cargoWeight; }
    public void setCargoWeight(double cargoWeight) { this.cargoWeight = cargoWeight; }

    public double getPlannedDistance() { return plannedDistance; }
    public void setPlannedDistance(double plannedDistance) { this.plannedDistance = plannedDistance; }

    public LocalDateTime getDispatchTime() { return dispatchTime; }
    public void setDispatchTime(LocalDateTime dispatchTime) { this.dispatchTime = dispatchTime; }

    public LocalDateTime getExpectedArrival() { return expectedArrival; }
    public void setExpectedArrival(LocalDateTime expectedArrival) { this.expectedArrival = expectedArrival; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
