package com.transitops.backend.adapter.in.rest.dto;

public class DriverVehicleResponse {
    private String vehicleNumber;
    private String vehicleModel;
    private String vehicleType;
    private String registrationNumber;
    private double maxCapacity;
    private double currentOdometer;
    private String vehicleStatus;
    private String lastMaintenanceDate;
    private String fuelEfficiency;

    public DriverVehicleResponse() {}

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public double getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(double maxCapacity) { this.maxCapacity = maxCapacity; }

    public double getCurrentOdometer() { return currentOdometer; }
    public void setCurrentOdometer(double currentOdometer) { this.currentOdometer = currentOdometer; }

    public String getVehicleStatus() { return vehicleStatus; }
    public void setVehicleStatus(String vehicleStatus) { this.vehicleStatus = vehicleStatus; }

    public String getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(String lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }

    public String getFuelEfficiency() { return fuelEfficiency; }
    public void setFuelEfficiency(String fuelEfficiency) { this.fuelEfficiency = fuelEfficiency; }
}
