package com.transitops.backend.domain;

import java.math.BigDecimal;

public class Vehicle {

    private Long id;
    private String registrationNumber;
    private String nameModel;
    private String vehicleType;
    private BigDecimal maxLoadCapacity;
    private BigDecimal odometer;
    private BigDecimal acquisitionCost;
    private String status;
    private String region;
    private Integer healthScore;
    private String priority;

    public Vehicle() {}

    public Vehicle(Long id, String registrationNumber, String nameModel, String vehicleType,
                   BigDecimal maxLoadCapacity, BigDecimal odometer, BigDecimal acquisitionCost,
                   String status, String region, Integer healthScore, String priority) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.nameModel = nameModel;
        this.vehicleType = vehicleType;
        this.maxLoadCapacity = maxLoadCapacity;
        this.odometer = odometer;
        this.acquisitionCost = acquisitionCost;
        this.status = status;
        this.region = region;
        this.healthScore = healthScore;
        this.priority = priority;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getNameModel() { return nameModel; }
    public void setNameModel(String nameModel) { this.nameModel = nameModel; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public BigDecimal getMaxLoadCapacity() { return maxLoadCapacity; }
    public void setMaxLoadCapacity(BigDecimal maxLoadCapacity) { this.maxLoadCapacity = maxLoadCapacity; }

    public BigDecimal getOdometer() { return odometer; }
    public void setOdometer(BigDecimal odometer) { this.odometer = odometer; }

    public BigDecimal getAcquisitionCost() { return acquisitionCost; }
    public void setAcquisitionCost(BigDecimal acquisitionCost) { this.acquisitionCost = acquisitionCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
