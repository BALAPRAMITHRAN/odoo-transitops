package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class DashboardMetricsDTO {
    private FleetReadinessDTO fleetReadiness;
    private VehicleHealthSummaryDTO vehicleHealthSummary;
    private OperationalInsightsDTO operationalInsights;
    private ActionCenterDTO actionCenter;

    public DashboardMetricsDTO() {}

    public DashboardMetricsDTO(FleetReadinessDTO fleetReadiness, 
                              VehicleHealthSummaryDTO vehicleHealthSummary,
                              OperationalInsightsDTO operationalInsights,
                              ActionCenterDTO actionCenter) {
        this.fleetReadiness = fleetReadiness;
        this.vehicleHealthSummary = vehicleHealthSummary;
        this.operationalInsights = operationalInsights;
        this.actionCenter = actionCenter;
    }

    public FleetReadinessDTO getFleetReadiness() { return fleetReadiness; }
    public void setFleetReadiness(FleetReadinessDTO fleetReadiness) { this.fleetReadiness = fleetReadiness; }

    public VehicleHealthSummaryDTO getVehicleHealthSummary() { return vehicleHealthSummary; }
    public void setVehicleHealthSummary(VehicleHealthSummaryDTO vehicleHealthSummary) { this.vehicleHealthSummary = vehicleHealthSummary; }

    public OperationalInsightsDTO getOperationalInsights() { return operationalInsights; }
    public void setOperationalInsights(OperationalInsightsDTO operationalInsights) { this.operationalInsights = operationalInsights; }

    public ActionCenterDTO getActionCenter() { return actionCenter; }
    public void setActionCenter(ActionCenterDTO actionCenter) { this.actionCenter = actionCenter; }

    public static class FleetReadinessDTO {
        private Integer totalFleet;
        private Integer available;
        private Integer onTrip;
        private Integer inMaintenance;
        private Integer retired;
        private Double fleetReadinessScore;
        private Double operationalAvailability;

        public FleetReadinessDTO() {}

        public FleetReadinessDTO(Integer totalFleet, Integer available, Integer onTrip, 
                                Integer inMaintenance, Integer retired, 
                                Double fleetReadinessScore, Double operationalAvailability) {
            this.totalFleet = totalFleet;
            this.available = available;
            this.onTrip = onTrip;
            this.inMaintenance = inMaintenance;
            this.retired = retired;
            this.fleetReadinessScore = fleetReadinessScore;
            this.operationalAvailability = operationalAvailability;
        }

        public Integer getTotalFleet() { return totalFleet; }
        public void setTotalFleet(Integer totalFleet) { this.totalFleet = totalFleet; }

        public Integer getAvailable() { return available; }
        public void setAvailable(Integer available) { this.available = available; }

        public Integer getOnTrip() { return onTrip; }
        public void setOnTrip(Integer onTrip) { this.onTrip = onTrip; }

        public Integer getInMaintenance() { return inMaintenance; }
        public void setInMaintenance(Integer inMaintenance) { this.inMaintenance = inMaintenance; }

        public Integer getRetired() { return retired; }
        public void setRetired(Integer retired) { this.retired = retired; }

        public Double getFleetReadinessScore() { return fleetReadinessScore; }
        public void setFleetReadinessScore(Double fleetReadinessScore) { this.fleetReadinessScore = fleetReadinessScore; }

        public Double getOperationalAvailability() { return operationalAvailability; }
        public void setOperationalAvailability(Double operationalAvailability) { this.operationalAvailability = operationalAvailability; }
    }

    public static class VehicleHealthSummaryDTO {
        private Integer available;
        private Integer good;
        private Integer needsService;
        private Integer critical;
        private Double averageVehicleHealth;

        public VehicleHealthSummaryDTO() {}

        public VehicleHealthSummaryDTO(Integer available, Integer good, Integer needsService, 
                                      Integer critical, Double averageVehicleHealth) {
            this.available = available;
            this.good = good;
            this.needsService = needsService;
            this.critical = critical;
            this.averageVehicleHealth = averageVehicleHealth;
        }

        public Integer getAvailable() { return available; }
        public void setAvailable(Integer available) { this.available = available; }

        public Integer getGood() { return good; }
        public void setGood(Integer good) { this.good = good; }

        public Integer getNeedsService() { return needsService; }
        public void setNeedsService(Integer needsService) { this.needsService = needsService; }

        public Integer getCritical() { return critical; }
        public void setCritical(Integer critical) { this.critical = critical; }

        public Double getAverageVehicleHealth() { return averageVehicleHealth; }
        public void setAverageVehicleHealth(Double averageVehicleHealth) { this.averageVehicleHealth = averageVehicleHealth; }
    }

    public static class OperationalInsightsDTO {
        private List<String> insights;

        public OperationalInsightsDTO() {}

        public OperationalInsightsDTO(List<String> insights) {
            this.insights = insights;
        }

        public List<String> getInsights() { return insights; }
        public void setInsights(List<String> insights) { this.insights = insights; }
    }

    public static class ActionCenterDTO {
        private List<VehicleActionItem> vehiclesRequiringMaintenance;
        private List<VehicleActionItem> highestPriorityVehicles;
        private List<VehicleActionItem> upcomingMaintenance;

        public ActionCenterDTO() {}

        public ActionCenterDTO(List<VehicleActionItem> vehiclesRequiringMaintenance,
                             List<VehicleActionItem> highestPriorityVehicles,
                             List<VehicleActionItem> upcomingMaintenance) {
            this.vehiclesRequiringMaintenance = vehiclesRequiringMaintenance;
            this.highestPriorityVehicles = highestPriorityVehicles;
            this.upcomingMaintenance = upcomingMaintenance;
        }

        public List<VehicleActionItem> getVehiclesRequiringMaintenance() { return vehiclesRequiringMaintenance; }
        public void setVehiclesRequiringMaintenance(List<VehicleActionItem> vehiclesRequiringMaintenance) { this.vehiclesRequiringMaintenance = vehiclesRequiringMaintenance; }

        public List<VehicleActionItem> getHighestPriorityVehicles() { return highestPriorityVehicles; }
        public void setHighestPriorityVehicles(List<VehicleActionItem> highestPriorityVehicles) { this.highestPriorityVehicles = highestPriorityVehicles; }

        public List<VehicleActionItem> getUpcomingMaintenance() { return upcomingMaintenance; }
        public void setUpcomingMaintenance(List<VehicleActionItem> upcomingMaintenance) { this.upcomingMaintenance = upcomingMaintenance; }
    }

    public static class VehicleActionItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private String status;
        private Integer healthScore;
        private String priority;

        public VehicleActionItem() {}

        public VehicleActionItem(Long id, String registrationNumber, String nameModel, 
                               String status, Integer healthScore, String priority) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.status = status;
            this.healthScore = healthScore;
            this.priority = priority;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getRegistrationNumber() { return registrationNumber; }
        public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

        public String getNameModel() { return nameModel; }
        public void setNameModel(String nameModel) { this.nameModel = nameModel; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Integer getHealthScore() { return healthScore; }
        public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

        public String getPriority() { return priority; }
        public void setPriority(String priority) { this.priority = priority; }
    }
}
