package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class CostOfOwnershipDTO {
    private List<VehicleCostItem> vehicles;

    public CostOfOwnershipDTO(List<VehicleCostItem> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleCostItem> getVehicles() { return vehicles; }

    public static class VehicleCostItem {
        private Long id;
        private String registrationNumber;
        private String nameModel;
        private double totalMaintenanceCost;
        private double ownershipRatio;
        private boolean considerRetirement;

        public VehicleCostItem(Long id, String registrationNumber, String nameModel, double totalMaintenanceCost, double ownershipRatio, boolean considerRetirement) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.nameModel = nameModel;
            this.totalMaintenanceCost = totalMaintenanceCost;
            this.ownershipRatio = ownershipRatio;
            this.considerRetirement = considerRetirement;
        }

        public Long getId() { return id; }
        public String getRegistrationNumber() { return registrationNumber; }
        public String getNameModel() { return nameModel; }
        public double getTotalMaintenanceCost() { return totalMaintenanceCost; }
        public double getOwnershipRatio() { return ownershipRatio; }
        public boolean isConsiderRetirement() { return considerRetirement; }
    }
}
