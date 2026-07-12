package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class ActionCenterDTO {
    private List<VehicleMaintenanceItem> vehiclesRequiringMaintenance;
    private List<VehiclePriorityItem> highestPriorityVehicles;
    private List<VehicleUpcomingItem> upcomingMaintenance;

    public ActionCenterDTO(List<VehicleMaintenanceItem> vehiclesRequiringMaintenance, List<VehiclePriorityItem> highestPriorityVehicles, List<VehicleUpcomingItem> upcomingMaintenance) {
        this.vehiclesRequiringMaintenance = vehiclesRequiringMaintenance;
        this.highestPriorityVehicles = highestPriorityVehicles;
        this.upcomingMaintenance = upcomingMaintenance;
    }

    public List<VehicleMaintenanceItem> getVehiclesRequiringMaintenance() { return vehiclesRequiringMaintenance; }
    public List<VehiclePriorityItem> getHighestPriorityVehicles() { return highestPriorityVehicles; }
    public List<VehicleUpcomingItem> getUpcomingMaintenance() { return upcomingMaintenance; }

    public static class VehicleMaintenanceItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private long kmSinceService;

        public VehicleMaintenanceItem(Long id, String registrationNumber, String nameModel, long kmSinceService) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.kmSinceService = kmSinceService;
        }

        public Long getId() { return id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public String getNameModel() { return nameModel; }
        public long getKmSinceService() { return kmSinceService; }
    }

    public static class VehiclePriorityItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private int healthScore;
        private String status;

        public VehiclePriorityItem(Long id, String registrationNumber, String nameModel, int healthScore, String status) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.healthScore = healthScore;
            this.status = status;
        }

        public Long getId() { return id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public String getNameModel() { return nameModel; }
        public int getHealthScore() { return healthScore; }
        public String getStatus() { return status; }
    }

    public static class VehicleUpcomingItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private long daysSinceLastService;

        public VehicleUpcomingItem(Long id, String registrationNumber, String nameModel, long daysSinceLastService) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.daysSinceLastService = daysSinceLastService;
        }

        public Long getId() { return id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public String getNameModel() { return nameModel; }
        public long getDaysSinceLastService() { return daysSinceLastService; }
    }
}
