package com.transitops.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MaintenanceLog {

    private Long id;
    private Long vehicleId;
    private String description;
    private BigDecimal cost;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private BigDecimal odometerAtService;

    public MaintenanceLog() {}

    public MaintenanceLog(Long id, Long vehicleId, String description, BigDecimal cost,
                          String status, LocalDateTime createdAt, LocalDateTime closedAt, BigDecimal odometerAtService) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.description = description;
        this.cost = cost;
        this.status = status;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
        this.odometerAtService = odometerAtService;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public BigDecimal getOdometerAtService() { return odometerAtService; }
    public void setOdometerAtService(BigDecimal odometerAtService) { this.odometerAtService = odometerAtService; }
}
