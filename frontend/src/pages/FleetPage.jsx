import { useState, useEffect, useCallback } from 'react';

const API = 'http://localhost:8080/api';

function FleetPage() {
  const [vehicles, setVehicles] = useState([]);
  const [statusFilter, setStatusFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [regionFilter, setRegionFilter] = useState('');
  const [showRegisterForm, setShowRegisterForm] = useState(false);
  const [maintenanceVehicleId, setMaintenanceVehicleId] = useState(null);
  const [logsData, setLogsData] = useState(null);
  const [logsVehicleId, setLogsVehicleId] = useState(null);
  const [error, setError] = useState('');
  const [selectedVehicle, setSelectedVehicle] = useState(null);
  const [showDetailsPanel, setShowDetailsPanel] = useState(false);
  const [actionMenuVehicleId, setActionMenuVehicleId] = useState(null);

  const [registerForm, setRegisterForm] = useState({
    registrationNumber: '',
    nameModel: '',
    vehicleType: '',
    maxLoadCapacity: '',
    odometer: '',
    acquisitionCost: '',
    region: '',
  });

  const [maintForm, setMaintForm] = useState({ description: '', cost: '' });

  const fetchVehicles = useCallback(() => {
    fetch(`${API}/vehicles`)
      .then(r => {
        if (!r.ok) throw new Error('Failed to fetch vehicles');
        return r.json();
      })
      .then(setVehicles)
      .catch(err => {
        console.error('Fetch error:', err);
        setError('Failed to fetch vehicles');
      });
  }, []);

  useEffect(() => {
    fetchVehicles();
  }, [fetchVehicles]);

  const filteredVehicles = vehicles.filter(v => {
    if (statusFilter && v.status !== statusFilter) return false;
    if (
      typeFilter &&
      !(v.vehicleType || '').toLowerCase().includes(typeFilter.toLowerCase())
    )
      return false;
    if (
      regionFilter &&
      !(v.region || '').toLowerCase().includes(regionFilter.toLowerCase())
    )
      return false;
    return true;
  });

  const statusBadgeClass = status => {
    return `badge badge-${status.toLowerCase().replace(/\s+/g, '')}`;
  };

  const registerVehicle = e => {
    e.preventDefault();
    setError('');
    fetch(`${API}/vehicles`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        registrationNumber: registerForm.registrationNumber,
        nameModel: registerForm.nameModel,
        vehicleType: registerForm.vehicleType,
        maxLoadCapacity: registerForm.maxLoadCapacity
          ? Number(registerForm.maxLoadCapacity)
          : null,
        odometer: registerForm.odometer ? Number(registerForm.odometer) : 0,
        acquisitionCost: registerForm.acquisitionCost
          ? Number(registerForm.acquisitionCost)
          : null,
        region: registerForm.region,
      }),
    })
      .then(r => {
        if (!r.ok)
          return r.json().then(d => {
            throw new Error(d.error || 'Registration failed');
          });
        return r.json();
      })
      .then(() => {
        setShowRegisterForm(false);
        setRegisterForm({
          registrationNumber: '',
          nameModel: '',
          vehicleType: '',
          maxLoadCapacity: '',
          odometer: '',
          acquisitionCost: '',
          region: '',
        });
        fetchVehicles();
      })
      .catch(err => setError(err.message));
  };

  const retireVehicle = id => {
    fetch(`${API}/vehicles/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ status: 'Retired' }),
    }).then(() => fetchVehicles());
  };

  const createMaintenance = e => {
    e.preventDefault();
    fetch(`${API}/vehicles/${maintenanceVehicleId}/maintenance`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        description: maintForm.description,
        cost: Number(maintForm.cost),
      }),
    }).then(() => {
      setMaintenanceVehicleId(null);
      setMaintForm({ description: '', cost: '' });
      fetchVehicles();
    });
  };

  const viewLogs = vehicleId => {
    setLogsVehicleId(vehicleId);
    fetch(`${API}/vehicles/${vehicleId}/maintenance`)
      .then(r => r.json())
      .then(setLogsData);
  };

  const viewVehicleDetails = vehicle => {
    setSelectedVehicle(vehicle);
    setShowDetailsPanel(true);
  };

  const toggleActionMenu = (e, vehicleId) => {
    e.stopPropagation();
    setActionMenuVehicleId(actionMenuVehicleId === vehicleId ? null : vehicleId);
  };

  const handleAction = (action, vehicle) => {
    setActionMenuVehicleId(null);
    if (action === 'maintenance') {
      setMaintenanceVehicleId(vehicle.id);
    } else if (action === 'retire') {
      retireVehicle(vehicle.id);
    } else if (action === 'details') {
      viewVehicleDetails(vehicle);
    } else if (action === 'logs') {
      viewLogs(vehicle.id);
    }
  };

  const closeLog = logId => {
    fetch(`${API}/maintenance/${logId}/close`, { method: 'PUT' }).then(() => {
      viewLogs(logsVehicleId);
      fetchVehicles();
    });
  };

  return (
    <div onClick={() => setActionMenuVehicleId(null)}>
      <div className="page-header">
        <div className="page-header-left">
          <h1 className="page-title">Fleet</h1>
          <p className="page-subtitle">Vehicle registry and management</p>
        </div>
        <button
          className="btn btn-primary"
          onClick={() => setShowRegisterForm(true)}
        >
          + Register Vehicle
        </button>
      </div>

      <div className="filter-toolbar">
        <div className="filter-group">
          <label className="filter-label">Search</label>
          <input
            className="filter-input"
            placeholder="Search vehicles..."
            value={typeFilter}
            onChange={e => setTypeFilter(e.target.value)}
          />
        </div>
        <div className="filter-group">
          <label className="filter-label">Status</label>
          <select
            className="filter-select"
            value={statusFilter}
            onChange={e => setStatusFilter(e.target.value)}
          >
            <option value="">All Statuses</option>
            <option value="Available">Available</option>
            <option value="On Trip">On Trip</option>
            <option value="In Shop">In Shop</option>
            <option value="Retired">Retired</option>
          </select>
        </div>
        <div className="filter-group">
          <label className="filter-label">Region</label>
          <input
            className="filter-input"
            placeholder="Filter by region..."
            value={regionFilter}
            onChange={e => setRegionFilter(e.target.value)}
          />
        </div>
        <div className="filter-group">
          <label className="filter-label">Vehicle Type</label>
          <input
            className="filter-input"
            placeholder="Filter by type..."
            value={typeFilter}
            onChange={e => setTypeFilter(e.target.value)}
          />
        </div>
        <button
          className="btn btn-secondary"
          onClick={() => {
            setStatusFilter('');
            setTypeFilter('');
            setRegionFilter('');
          }}
        >
          Reset Filters
        </button>
      </div>

      {error && (
        <div className="error-banner">
          {error}
          <button className="error-close" onClick={() => setError('')}>
            ×
          </button>
        </div>
      )}

      <div className="table-container">
        <table className="table">
          <thead>
            <tr>
              <th>Registration Number</th>
              <th>Vehicle</th>
              <th>Type</th>
              <th>Capacity</th>
              <th>Region</th>
              <th>Health</th>
              <th>Priority</th>
              <th>Maintenance Due</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredVehicles.length === 0 ? (
              <tr>
                <td colSpan="10">
                  <div className="empty-state">
                    <p className="empty-state-text">No vehicles found</p>
                  </div>
                </td>
              </tr>
            ) : (
              filteredVehicles.map(v => (
                <tr key={v.id}>
                  <td style={{ fontWeight: 600 }}>{v.registrationNumber}</td>
                  <td>{v.nameModel}</td>
                  <td>{v.vehicleType}</td>
                  <td>{v.maxLoadCapacity?.toLocaleString()}</td>
                  <td>{v.region}</td>
                  <td>
                    <span className={`health-score health-${v.healthScore >= 80 ? 'good' : v.healthScore >= 50 ? 'warning' : 'critical'}`}>
                      {v.healthScore || 0}
                    </span>
                  </td>
                  <td>
                    <span className={`priority-badge priority-${(v.priority || 'medium').toLowerCase()}`}>
                      {v.priority || 'Medium'}
                    </span>
                  </td>
                  <td>
                    {v.healthScore < 70 ? 'Yes' : 'No'}
                  </td>
                  <td>
                    <span className={statusBadgeClass(v.status)}>
                      {v.status}
                    </span>
                  </td>
                  <td className="actions-cell">
                    <div className="action-dropdown">
                      <button
                        className="btn btn-sm btn-secondary"
                        onClick={(e) => toggleActionMenu(e, v.id)}
                      >
                        Actions
                      </button>
                      {actionMenuVehicleId === v.id && (
                        <div className="dropdown-menu">
                          <button
                            className="dropdown-item"
                            disabled={v.status === 'Retired' || v.status === 'In Shop'}
                            onClick={() => handleAction('maintenance', v)}
                          >
                            Send to Maintenance
                          </button>
                          <button
                            className="dropdown-item"
                            disabled={v.status === 'Retired'}
                            onClick={() => handleAction('retire', v)}
                          >
                            Retire
                          </button>
                          <button
                            className="dropdown-item"
                            onClick={() => handleAction('details', v)}
                          >
                            View Details
                          </button>
                          <button
                            className="dropdown-item"
                            onClick={() => handleAction('logs', v)}
                          >
                            Maintenance History
                          </button>
                        </div>
                      )}
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {showRegisterForm && (
        <div
          className="modal-overlay"
          onClick={() => setShowRegisterForm(false)}
        >
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h2 className="modal-title">Register New Vehicle</h2>
            {error && <div className="error-banner">{error}</div>}
            <form onSubmit={registerVehicle} className="form-grid">
              <div className="form-group">
                <label className="form-label">Registration Number *</label>
                <input
                  className="form-input"
                  required
                  value={registerForm.registrationNumber}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      registrationNumber: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Name / Model</label>
                <input
                  className="form-input"
                  value={registerForm.nameModel}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      nameModel: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Vehicle Type</label>
                <input
                  className="form-input"
                  value={registerForm.vehicleType}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      vehicleType: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Max Load Capacity</label>
                <input
                  className="form-input"
                  type="number"
                  step="0.01"
                  value={registerForm.maxLoadCapacity}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      maxLoadCapacity: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Odometer</label>
                <input
                  className="form-input"
                  type="number"
                  step="0.01"
                  value={registerForm.odometer}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      odometer: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Acquisition Cost</label>
                <input
                  className="form-input"
                  type="number"
                  step="0.01"
                  value={registerForm.acquisitionCost}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      acquisitionCost: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-group">
                <label className="form-label">Region</label>
                <input
                  className="form-input"
                  value={registerForm.region}
                  onChange={e =>
                    setRegisterForm({
                      ...registerForm,
                      region: e.target.value,
                    })
                  }
                />
              </div>
              <div className="form-actions">
                <button
                  type="button"
                  className="btn btn-ghost"
                  onClick={() => setShowRegisterForm(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  Register
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {maintenanceVehicleId && (
        <div
          className="modal-overlay"
          onClick={() => setMaintenanceVehicleId(null)}
        >
          <div
            className="modal modal-sm"
            onClick={e => e.stopPropagation()}
          >
            <h2 className="modal-title">Send to Maintenance</h2>
            <form onSubmit={createMaintenance}>
              <div className="form-group" style={{ marginBottom: 'var(--spacing-md)' }}>
                <label className="form-label">Description *</label>
                <input
                  className="form-input"
                  required
                  value={maintForm.description}
                  onChange={e =>
                    setMaintForm({ ...maintForm, description: e.target.value })
                  }
                />
              </div>
              <div className="form-group" style={{ marginBottom: 'var(--spacing-md)' }}>
                <label className="form-label">Cost *</label>
                <input
                  className="form-input"
                  required
                  type="number"
                  step="0.01"
                  value={maintForm.cost}
                  onChange={e =>
                    setMaintForm({ ...maintForm, cost: e.target.value })
                  }
                />
              </div>
              <div style={{ display: 'flex', justifyContent: 'flex-end', gap: 'var(--spacing-sm)' }}>
                <button
                  type="button"
                  className="btn btn-ghost"
                  onClick={() => setMaintenanceVehicleId(null)}
                >
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  Submit
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {logsData && (
        <div className="modal-overlay" onClick={() => setLogsData(null)}>
          <div
            className="modal modal-lg"
            onClick={e => e.stopPropagation()}
          >
            <div className="modal-header-row">
              <h2 className="modal-title">Maintenance Logs</h2>
              <button
                className="btn btn-ghost"
                onClick={() => setLogsData(null)}
              >
                ×
              </button>
            </div>
            {logsData.length === 0 ? (
              <div className="empty-state">
                <p className="empty-state-text">No maintenance logs found</p>
              </div>
            ) : (
              <div className="table-container">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Description</th>
                      <th>Cost</th>
                      <th>Status</th>
                      <th>Created</th>
                      <th>Closed</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {logsData.map(log => (
                      <tr key={log.id}>
                        <td>{log.description}</td>
                        <td>₹{log.cost?.toLocaleString()}</td>
                        <td>
                          <span
                            className={`badge ${log.status === 'ACTIVE' ? 'badge-warning' : 'badge-success'}`}
                          >
                            {log.status}
                          </span>
                        </td>
                        <td>
                          {log.createdAt
                            ? new Date(log.createdAt).toLocaleDateString()
                            : '-'}
                        </td>
                        <td>
                          {log.closedAt
                            ? new Date(log.closedAt).toLocaleDateString()
                            : '-'}
                        </td>
                        <td>
                          {log.status === 'ACTIVE' ? (
                            <button
                              className="btn btn-sm btn-primary"
                              onClick={() => closeLog(log.id)}
                            >
                              Close
                            </button>
                          ) : (
                            <span style={{ color: 'var(--color-text-tertiary)' }}>—</span>
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
      )}

      {showDetailsPanel && selectedVehicle && (
        <div
          className="side-panel-overlay"
          onClick={() => setShowDetailsPanel(false)}
        >
          <div
            className="side-panel"
            onClick={e => e.stopPropagation()}
          >
            <div className="side-panel-header">
              <h2 className="side-panel-title">Vehicle Details</h2>
              <button
                className="btn btn-ghost"
                onClick={() => setShowDetailsPanel(false)}
              >
                ×
              </button>
            </div>
            <div className="side-panel-content">
              <div className="info-card">
                <h3 className="info-card-title">General Information</h3>
                <div className="info-row">
                  <span className="info-label">Registration Number</span>
                  <span className="info-value">{selectedVehicle.registrationNumber}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Name/Model</span>
                  <span className="info-value">{selectedVehicle.nameModel || '-'}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Vehicle Type</span>
                  <span className="info-value">{selectedVehicle.vehicleType || '-'}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Region</span>
                  <span className="info-value">{selectedVehicle.region || '-'}</span>
                </div>
              </div>

              <div className="info-card">
                <h3 className="info-card-title">Operational Status</h3>
                <div className="info-row">
                  <span className="info-label">Status</span>
                  <span className={statusBadgeClass(selectedVehicle.status)}>
                    {selectedVehicle.status}
                  </span>
                </div>
                <div className="info-row">
                  <span className="info-label">Health Score</span>
                  <span className={`health-score health-${selectedVehicle.healthScore >= 80 ? 'good' : selectedVehicle.healthScore >= 50 ? 'warning' : 'critical'}`}>
                    {selectedVehicle.healthScore || 0}/100
                  </span>
                </div>
                <div className="info-row">
                  <span className="info-label">Priority</span>
                  <span className={`priority-badge priority-${(selectedVehicle.priority || 'medium').toLowerCase()}`}>
                    {selectedVehicle.priority || 'Medium'}
                  </span>
                </div>
              </div>

              <div className="info-card">
                <h3 className="info-card-title">Specifications</h3>
                <div className="info-row">
                  <span className="info-label">Max Load Capacity</span>
                  <span className="info-value">{selectedVehicle.maxLoadCapacity?.toLocaleString() || '-'}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Odometer</span>
                  <span className="info-value">{selectedVehicle.odometer?.toLocaleString() || '-'}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Acquisition Cost</span>
                  <span className="info-value">₹{selectedVehicle.acquisitionCost?.toLocaleString() || '-'}</span>
                </div>
              </div>

              <div className="info-card">
                <h3 className="info-card-title">Current Condition</h3>
                <div className="info-row">
                  <span className="info-label">Maintenance Due</span>
                  <span className="info-value">
                    {selectedVehicle.healthScore < 70 ? 'Yes - Schedule maintenance' : 'No'}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default FleetPage;
