import { useState, useEffect, useCallback } from 'react';

const API = 'http://localhost:8080/api';

function MaintenancePage() {
  const [logs, setLogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('All');

  const fetchAllLogs = useCallback(() => {
    setLoading(true);
    fetch(`${API}/vehicles`)
      .then(r => r.json())
      .then(vehicles => {
        const promises = vehicles.map(v =>
          fetch(`${API}/vehicles/${v.id}/maintenance`)
            .then(r => r.json())
            .then(mLogs =>
              mLogs.map(log => ({
                ...log,
                vehicleReg: v.registrationNumber,
                vehicleName: v.nameModel,
              }))
            )
        );
        return Promise.all(promises);
      })
      .then(results => {
        setLogs(results.flat());
        setLoading(false);
      })
      .catch(() => {
        setLogs([]);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    fetchAllLogs();
  }, [fetchAllLogs]);

  const closeLog = useCallback(
    (id) => {
      fetch(`${API}/maintenance/${id}/close`, { method: 'PUT' }).then(() => {
        fetchAllLogs();
      });
    },
    [fetchAllLogs]
  );

  const totalLogs = logs.length;
  const activeLogs = logs.filter(l => l.status === 'ACTIVE').length;
  const closedLogs = logs.filter(l => l.status === 'CLOSED').length;

  const filteredLogs =
    activeTab === 'All'
      ? logs
      : activeTab === 'Active'
        ? logs.filter(l => l.status === 'ACTIVE')
        : logs.filter(l => l.status === 'CLOSED');

  return (
    <div>
      <div className="page-header">
        <div className="page-header-left">
          <h1 className="page-title">Maintenance</h1>
          <p className="page-subtitle">Service logs and repair tracking</p>
        </div>
      </div>

      <div className="kpi-strip kpi-strip-3">
        <div className="kpi-card">
          <span className="kpi-card-value text-primary">{totalLogs}</span>
          <span className="kpi-card-label">Total Logs</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-value text-warning">{activeLogs}</span>
          <span className="kpi-card-label">Active</span>
        </div>
        <div className="kpi-card">
          <span className="kpi-card-value text-success">{closedLogs}</span>
          <span className="kpi-card-label">Closed</span>
        </div>
      </div>

      <div className="tab-bar">
        {['All', 'Active', 'Closed'].map(tab => (
          <button
            key={tab}
            className={`tab-btn${activeTab === tab ? ' active' : ''}`}
            onClick={() => setActiveTab(tab)}
          >
            {tab}
          </button>
        ))}
      </div>

      {loading ? (
        <div className="loading-state">
          <div className="spinner"></div>
          Loading maintenance records...
        </div>
      ) : filteredLogs.length === 0 ? (
        <div className="empty-state">
          <p className="empty-state-text">No maintenance records found</p>
        </div>
      ) : (
        <div className="table-container">
          <table className="table">
            <thead>
              <tr>
                <th>Vehicle</th>
                <th>Description</th>
                <th>Cost</th>
                <th>Status</th>
                <th>Created</th>
                <th>Closed</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {filteredLogs.map(log => (
                <tr key={`${log.id}-${log.vehicleReg}`}>
                  <td>
                    <div className="activity-item-info">
                      <span className="activity-item-name">{log.vehicleReg}</span>
                      {log.vehicleName && (
                        <span className="activity-item-reg">{log.vehicleName}</span>
                      )}
                    </div>
                  </td>
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
                        Close Log
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
  );
}

export default MaintenancePage;
