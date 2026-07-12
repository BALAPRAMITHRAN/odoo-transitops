import { useState, useEffect } from 'react';

const API = 'http://localhost:8080/api';

const HEALTH_CONFIG = [
  { key: 'excellent', label: 'Excellent', colorClass: 'text-success', barColor: 'var(--color-success)' },
  { key: 'good', label: 'Good', colorClass: 'text-info', barColor: 'var(--color-info)' },
  { key: 'needsAttention', label: 'Needs Attention', colorClass: 'text-warning', barColor: 'var(--color-warning)' },
  { key: 'critical', label: 'Critical', colorClass: 'text-danger', barColor: 'var(--color-danger)' },
];

function DashboardPage() {
  const [fleetHealth, setFleetHealth] = useState(null);
  const [insights, setInsights] = useState(null);
  const [actionCenter, setActionCenter] = useState(null);
  const [costOfOwnership, setCostOfOwnership] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      fetch(`${API}/dashboard/fleet-health`).then(r => {
        if (!r.ok) throw new Error('Failed to fetch fleet health');
        return r.json();
      }),
      fetch(`${API}/dashboard/insights`).then(r => {
        if (!r.ok) throw new Error('Failed to fetch insights');
        return r.json();
      }),
      fetch(`${API}/dashboard/action-center`).then(r => {
        if (!r.ok) throw new Error('Failed to fetch action center');
        return r.json();
      }),
      fetch(`${API}/dashboard/cost-of-ownership`).then(r => {
        if (!r.ok) throw new Error('Failed to fetch cost of ownership');
        return r.json();
      }),
    ])
      .then(([health, insightsData, action, cost]) => {
        setFleetHealth(health);
        setInsights(insightsData);
        setActionCenter(action);
        setCostOfOwnership(cost);
      })
      .catch(err => {
        console.error('Dashboard fetch error:', err);
      })
      .finally(() => setLoading(false));
  }, []);


  if (loading) {
    return (
      <div>
        <div className="page-header">
          <div className="page-header-left">
            <h1 className="page-title">Dashboard</h1>
            <p className="page-subtitle">Executive operational overview</p>
          </div>
        </div>
        <div className="loading-state">
          <div className="spinner"></div>
          Loading dashboard...
        </div>
      </div>
    );
  }

  return (
    <div>
      <div className="page-header">
        <div className="page-header-left">
          <h1 className="page-title">Dashboard</h1>
          <p className="page-subtitle">Executive operational overview</p>
        </div>
      </div>

      <div className="kpi-strip">
        <div className="kpi-card">
          <span className="kpi-card-label">Total Fleet</span>
          <span className="kpi-card-value text-primary">
            {fleetHealth?.vehicles?.length || 0}
          </span>
          <span className="kpi-card-trend">Registered vehicles</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-label">Excellent Health</span>
          <span className={`kpi-card-value text-success`}>
            {fleetHealth?.excellent || 0}
          </span>
          <span className="kpi-card-trend">80-100 health score</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-label">Critical Health</span>
          <span className={`kpi-card-value text-danger`}>
            {fleetHealth?.critical || 0}
          </span>
          <span className="kpi-card-trend">0-39 health score</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-label">Action Items</span>
          <span className={`kpi-card-value text-warning`}>
            {(actionCenter?.vehiclesRequiringMaintenance?.length || 0) + (actionCenter?.highestPriorityVehicles?.length || 0)}
          </span>
          <span className="kpi-card-trend">Require attention</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-label">Insights</span>
          <span className={`kpi-card-value text-info`}>
            {insights?.insights?.length || 0}
          </span>
          <span className="kpi-card-trend">Operational alerts</span>
        </div>
      </div>

      <div className="two-col-grid">
        <div className="card">
          <h2 className="card-title">Fleet Health Distribution</h2>
          {!fleetHealth ? (
            <div className="empty-state">
              <p className="empty-state-text">Loading health data...</p>
            </div>
          ) : (
            <div className="distribution-bar-container">
              <div className="distribution-bar">
                {HEALTH_CONFIG.map(seg => {
                  const count = fleetHealth[seg.key] || 0;
                  const total = (fleetHealth.excellent || 0) + (fleetHealth.good || 0) + (fleetHealth.needsAttention || 0) + (fleetHealth.critical || 0);
                  const percent = total > 0 ? (count / total) * 100 : 0;
                  return count > 0 ? (
                    <div
                      key={seg.key}
                      className="distribution-bar-segment"
                      style={{
                        width: `${percent}%`,
                        backgroundColor: seg.barColor,
                      }}
                      title={`${seg.label}: ${count} (${percent.toFixed(1)}%)`}
                    >
                      <span className="distribution-bar-label">{count}</span>
                    </div>
                  ) : null;
                })}
              </div>
              <div className="distribution-legend">
                {HEALTH_CONFIG.map(seg => {
                  const count = fleetHealth[seg.key] || 0;
                  const total = (fleetHealth.excellent || 0) + (fleetHealth.good || 0) + (fleetHealth.needsAttention || 0) + (fleetHealth.critical || 0);
                  const percent = total > 0 ? (count / total) * 100 : 0;
                  return (
                    <div className="legend-item" key={seg.key}>
                      <span
                        className="legend-dot"
                        style={{ backgroundColor: seg.barColor }}
                      />
                      <span>{seg.label}</span>
                      <span className="legend-value">
                        {count} ({percent.toFixed(1)}%)
                      </span>
                    </div>
                  );
                })}
              </div>
              <div className="critical-vehicles-list">
                <h4 className="critical-vehicles-title">Critical & Needs Attention Vehicles</h4>
                {fleetHealth.vehicles?.filter(v => v.bucket === 'Critical' || v.bucket === 'Needs Attention').map(v => (
                  <div className="critical-vehicle-item" key={v.id}>
                    <span className="critical-vehicle-reg">{v.registrationNumber}</span>
                    <span className={`badge ${v.bucket === 'Critical' ? 'badge-danger' : 'badge-warning'}`}>{v.bucket}</span>
                    <span className="critical-vehicle-score">Score: {v.healthScore}</span>
                  </div>
                ))}
                {fleetHealth.vehicles?.filter(v => v.bucket === 'Critical' || v.bucket === 'Needs Attention').length === 0 && (
                  <p className="action-empty">No vehicles in critical condition</p>
                )}
              </div>
            </div>
          )}
        </div>

        <div className="card">
          <h2 className="card-title">Operational Insights</h2>
          {!insights ? (
            <div className="empty-state">
              <p className="empty-state-text">Loading insights...</p>
            </div>
          ) : insights.insights?.length === 0 ? (
            <div className="empty-state">
              <p className="empty-state-text">No insights available</p>
            </div>
          ) : (
            <div className="insights-list">
              {insights.insights.map((insight, index) => (
                <div className="insight-item" key={index}>
                  <span className="insight-bullet">⚠</span>
                  <span className="insight-text">{insight}</span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <div className="card">
        <h2 className="card-title">Action Center</h2>
        {!actionCenter ? (
          <div className="empty-state">
            <p className="empty-state-text">Loading action center...</p>
          </div>
        ) : (
          <div className="action-center-grid">
            <div className="action-section">
              <h3 className="action-section-title">Vehicles Requiring Maintenance</h3>
              {actionCenter.vehiclesRequiringMaintenance?.length === 0 ? (
                <p className="action-empty">No vehicles requiring maintenance</p>
              ) : (
                <div className="action-list">
                  {actionCenter.vehiclesRequiringMaintenance?.map(v => (
                    <div className="action-item" key={v.id}>
                      <span className="action-item-reg">{v.registrationNumber}</span>
                      <span className="action-item-model">{v.nameModel}</span>
                      <span className="action-item-health">{v.kmSinceService.toLocaleString()} km since service</span>
                    </div>
                  ))}
                </div>
              )}
            </div>
            <div className="action-section">
              <h3 className="action-section-title">Highest Priority Vehicles</h3>
              {actionCenter.highestPriorityVehicles?.length === 0 ? (
                <p className="action-empty">No high-priority vehicles</p>
              ) : (
                <div className="action-list">
                  {actionCenter.highestPriorityVehicles?.map(v => (
                    <div className="action-item" key={v.id}>
                      <span className="action-item-reg">{v.registrationNumber}</span>
                      <span className="action-item-model">{v.nameModel}</span>
                      <span className="action-item-health">Health: {v.healthScore}</span>
                    </div>
                  ))}
                </div>
              )}
            </div>
            <div className="action-section">
              <h3 className="action-section-title">Upcoming Maintenance</h3>
              {actionCenter.upcomingMaintenance?.length === 0 ? (
                <p className="action-empty">No upcoming maintenance</p>
              ) : (
                <div className="action-list">
                  {actionCenter.upcomingMaintenance?.map(v => (
                    <div className="action-item" key={v.id}>
                      <span className="action-item-reg">{v.registrationNumber}</span>
                      <span className="action-item-model">{v.nameModel}</span>
                      <span className="action-item-health">{v.daysSinceLastService} days since last service</span>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </div>

      <div className="card">
        <h2 className="card-title">Cost of Ownership Watch</h2>
        {!costOfOwnership ? (
          <div className="empty-state">
            <p className="empty-state-text">Loading cost data...</p>
          </div>
        ) : costOfOwnership.vehicles?.length === 0 ? (
          <div className="empty-state">
            <p className="empty-state-text">No cost data available</p>
          </div>
        ) : (
          <div className="table-container">
            <table className="table">
              <thead>
                <tr>
                  <th>Registration Number</th>
                  <th>Vehicle</th>
                  <th>Total Maintenance Cost</th>
                  <th>Ownership Ratio</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {costOfOwnership.vehicles.map(v => (
                  <tr key={v.id}>
                    <td style={{ fontWeight: 600 }}>{v.registrationNumber}</td>
                    <td>{v.nameModel}</td>
                    <td>₹{v.totalMaintenanceCost.toLocaleString()}</td>
                    <td>{(v.ownershipRatio * 100).toFixed(1)}%</td>
                    <td>
                      {v.considerRetirement ? (
                        <span className="badge badge-retired">Consider Retirement</span>
                      ) : (
                        <span className="badge badge-available">Normal</span>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default DashboardPage;
