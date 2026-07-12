package com.transitops.backend.adapter.in.rest;

import com.transitops.backend.adapter.in.rest.dto.ActionCenterDTO;
import com.transitops.backend.adapter.in.rest.dto.CostOfOwnershipDTO;
import com.transitops.backend.adapter.in.rest.dto.DashboardMetricsDTO;
import com.transitops.backend.adapter.in.rest.dto.FleetHealthDTO;
import com.transitops.backend.adapter.in.rest.dto.OperationalInsightsDTO;
import com.transitops.backend.application.usecase.ActionCenterUseCase;
import com.transitops.backend.application.usecase.CloseMaintenanceLogUseCase;
import com.transitops.backend.application.usecase.CostOfOwnershipUseCase;
import com.transitops.backend.application.usecase.CreateMaintenanceLogUseCase;
import com.transitops.backend.application.usecase.DashboardMetricsUseCase;
import com.transitops.backend.application.usecase.ListVehiclesUseCase;
import com.transitops.backend.application.usecase.RegisterVehicleUseCase;
import com.transitops.backend.application.usecase.RepeatMaintenanceInsightUseCase;
import com.transitops.backend.application.usecase.UpdateVehicleStatusUseCase;
import com.transitops.backend.application.usecase.VehicleHealthScoreUseCase;
import com.transitops.backend.domain.MaintenanceLog;
import com.transitops.backend.domain.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VehicleController {

    private final RegisterVehicleUseCase registerVehicleUseCase;
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;
    private final CreateMaintenanceLogUseCase createMaintenanceLogUseCase;
    private final CloseMaintenanceLogUseCase closeMaintenanceLogUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final DashboardMetricsUseCase dashboardMetricsUseCase;
    private final VehicleHealthScoreUseCase vehicleHealthScoreUseCase;
    private final RepeatMaintenanceInsightUseCase repeatMaintenanceInsightUseCase;
    private final ActionCenterUseCase actionCenterUseCase;
    private final CostOfOwnershipUseCase costOfOwnershipUseCase;

    public VehicleController(RegisterVehicleUseCase registerVehicleUseCase,
                             UpdateVehicleStatusUseCase updateVehicleStatusUseCase,
                             CreateMaintenanceLogUseCase createMaintenanceLogUseCase,
                             CloseMaintenanceLogUseCase closeMaintenanceLogUseCase,
                             ListVehiclesUseCase listVehiclesUseCase,
                             DashboardMetricsUseCase dashboardMetricsUseCase,
                             VehicleHealthScoreUseCase vehicleHealthScoreUseCase,
                             RepeatMaintenanceInsightUseCase repeatMaintenanceInsightUseCase,
                             ActionCenterUseCase actionCenterUseCase,
                             CostOfOwnershipUseCase costOfOwnershipUseCase) {
        this.registerVehicleUseCase = registerVehicleUseCase;
        this.updateVehicleStatusUseCase = updateVehicleStatusUseCase;
        this.createMaintenanceLogUseCase = createMaintenanceLogUseCase;
        this.closeMaintenanceLogUseCase = closeMaintenanceLogUseCase;
        this.listVehiclesUseCase = listVehiclesUseCase;
        this.dashboardMetricsUseCase = dashboardMetricsUseCase;
        this.vehicleHealthScoreUseCase = vehicleHealthScoreUseCase;
        this.repeatMaintenanceInsightUseCase = repeatMaintenanceInsightUseCase;
        this.actionCenterUseCase = actionCenterUseCase;
        this.costOfOwnershipUseCase = costOfOwnershipUseCase;
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle) {
        Vehicle created = registerVehicleUseCase.execute(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> listVehicles(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(listVehiclesUseCase.listAll(status));
    }

    @GetMapping("/vehicles/available")
    public ResponseEntity<List<Vehicle>> listAvailableVehicles() {
        return ResponseEntity.ok(listVehiclesUseCase.listAvailable());
    }

    @GetMapping("/vehicles/dashboard-summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary() {
        return ResponseEntity.ok(listVehiclesUseCase.getDashboardSummary());
    }

    @GetMapping("/vehicles/dashboard-metrics")
    public ResponseEntity<DashboardMetricsDTO> getDashboardMetrics() {
        return ResponseEntity.ok(dashboardMetricsUseCase.execute());
    }

    @PutMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        Vehicle updated = updateVehicleStatusUseCase.execute(id, vehicle);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/vehicles/{id}/maintenance")
    public ResponseEntity<MaintenanceLog> createMaintenanceLog(@PathVariable Long id,
                                                                @RequestBody MaintenanceLog log) {
        MaintenanceLog created = createMaintenanceLogUseCase.execute(id, log);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/maintenance/{id}/close")
    public ResponseEntity<MaintenanceLog> closeMaintenanceLog(@PathVariable Long id) {
        MaintenanceLog closed = closeMaintenanceLogUseCase.execute(id);
        return ResponseEntity.ok(closed);
    }

    @GetMapping("/vehicles/{id}/maintenance")
    public ResponseEntity<List<MaintenanceLog>> getMaintenanceLogs(@PathVariable Long id) {
        return ResponseEntity.ok(listVehiclesUseCase.listMaintenanceLogs(id));
    }

    @GetMapping("/dashboard/fleet-health")
    public ResponseEntity<FleetHealthDTO> getFleetHealth() {
        return ResponseEntity.ok(vehicleHealthScoreUseCase.execute());
    }

    @GetMapping("/dashboard/insights")
    public ResponseEntity<OperationalInsightsDTO> getOperationalInsights() {
        return ResponseEntity.ok(repeatMaintenanceInsightUseCase.execute());
    }

    @GetMapping("/dashboard/action-center")
    public ResponseEntity<ActionCenterDTO> getActionCenter() {
        return ResponseEntity.ok(actionCenterUseCase.execute());
    }

    @GetMapping("/dashboard/cost-of-ownership")
    public ResponseEntity<CostOfOwnershipDTO> getCostOfOwnership() {
        return ResponseEntity.ok(costOfOwnershipUseCase.execute());
    }
}
