package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class FleetHealthDTO {
    private int excellent;
    private int good;
    private int needsAttention;
    private int critical;
    private List<VehicleHealthItem> vehicles;

    public FleetHealthDTO(int excellent, int good, int needsAttention, int critical, List<VehicleHealthItem> vehicles) {
        this.excellent = excellent;
        this.good = good;
        this.needsAttention = needsAttention;
        this.critical = critical;
        this.vehicles = vehicles;
    }

    public int getExcellent() { return excellent; }
    public int getGood() { return good; }
    public int getNeedsAttention() { return needsAttention; }
    public int getCritical() { return critical; }
    public List<VehicleHealthItem> getVehicles() { return vehicles; }

    public static class VehicleHealthItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private int healthScore;
        private String bucket;

        public VehicleHealthItem(Long id, String registrationNumber, String nameModel, int healthScore, String bucket) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.healthScore = healthScore;
            this.bucket = bucket;
        }

        public Long getId() { return id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public String getNameModel() { return nameModel; }
        public int getHealthScore() { return healthScore; }
        public String getBucket() { return bucket; }
    }
}
