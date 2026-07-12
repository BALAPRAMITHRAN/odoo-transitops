package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class DriverDashboardResponse {
    private int assignedTrips;
    private int activeDeliveries;
    private int completedDeliveries;
    private String assignedVehicleNumber;
    private String driverStatus;
    private List<DriverTripResponse> recentTrips;
    private List<DriverTripResponse> upcomingDeliveries;
    private List<String> recentActivity;

    public DriverDashboardResponse() {}

    public int getAssignedTrips() { return assignedTrips; }
    public void setAssignedTrips(int assignedTrips) { this.assignedTrips = assignedTrips; }

    public int getActiveDeliveries() { return activeDeliveries; }
    public void setActiveDeliveries(int activeDeliveries) { this.activeDeliveries = activeDeliveries; }

    public int getCompletedDeliveries() { return completedDeliveries; }
    public void setCompletedDeliveries(int completedDeliveries) { this.completedDeliveries = completedDeliveries; }

    public String getAssignedVehicleNumber() { return assignedVehicleNumber; }
    public void setAssignedVehicleNumber(String assignedVehicleNumber) { this.assignedVehicleNumber = assignedVehicleNumber; }

    public String getDriverStatus() { return driverStatus; }
    public void setDriverStatus(String driverStatus) { this.driverStatus = driverStatus; }

    public List<DriverTripResponse> getRecentTrips() { return recentTrips; }
    public void setRecentTrips(List<DriverTripResponse> recentTrips) { this.recentTrips = recentTrips; }

    public List<DriverTripResponse> getUpcomingDeliveries() { return upcomingDeliveries; }
    public void setUpcomingDeliveries(List<DriverTripResponse> upcomingDeliveries) { this.upcomingDeliveries = upcomingDeliveries; }

    public List<String> getRecentActivity() { return recentActivity; }
    public void setRecentActivity(List<String> recentActivity) { this.recentActivity = recentActivity; }
}
