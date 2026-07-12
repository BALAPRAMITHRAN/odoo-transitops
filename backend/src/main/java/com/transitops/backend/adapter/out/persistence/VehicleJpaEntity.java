package com.transitops.backend.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
public class VehicleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_number", unique = true, nullable = false, length = 30)
    private String registrationNumber;

    @Column(name = "name_model", length = 100)
    private String nameModel;

    @Column(name = "vehicle_type", length = 50)
    private String vehicleType;

    @Column(name = "max_load_capacity", precision = 10, scale = 2)
    private BigDecimal maxLoadCapacity;

    @Column(name = "odometer", precision = 10, scale = 2)
    private BigDecimal odometer;

    @Column(name = "acquisition_cost", precision = 12, scale = 2)
    private BigDecimal acquisitionCost;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "health_score")
    private Integer healthScore;

    @Column(name = "priority", length = 20)
    private String priority;

    public VehicleJpaEntity() {}

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
