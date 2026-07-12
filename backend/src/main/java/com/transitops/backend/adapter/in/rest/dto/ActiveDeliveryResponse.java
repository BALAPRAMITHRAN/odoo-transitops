package com.transitops.backend.adapter.in.rest.dto;

import java.time.LocalDateTime;

public class ActiveDeliveryResponse {
    private Long id;
    private String vehicleName;
    private String source;
    private String destination;
    private double cargoWeight;
    private double distance;
    private LocalDateTime dispatchTime;
    private LocalDateTime expectedArrival;
    private String currentStatus;

    public ActiveDeliveryResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleName() { return vehicleName; }
    public void setVehicleName(String vehicleName) { this.vehicleName = vehicleName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getCargoWeight() { return cargoWeight; }
    public void setCargoWeight(double cargoWeight) { this.cargoWeight = cargoWeight; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public LocalDateTime getDispatchTime() { return dispatchTime; }
    public void setDispatchTime(LocalDateTime dispatchTime) { this.dispatchTime = dispatchTime; }

    public LocalDateTime getExpectedArrival() { return expectedArrival; }
    public void setExpectedArrival(LocalDateTime expectedArrival) { this.expectedArrival = expectedArrival; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
}
