import { BrowserRouter, Routes, Route, NavLink, Navigate } from 'react-router-dom';
import { useState } from 'react';
import DashboardPage from './pages/DashboardPage';
import FleetPage from './pages/FleetPage';
import MaintenancePage from './pages/MaintenancePage';
import PlaceholderPage from './pages/PlaceholderPage';

const NAV_SECTIONS = [
  {
    label: 'Overview',
    items: [
      { to: '/dashboard', label: 'Dashboard' },
    ],
  },
  {
    label: 'Fleet Management',
    items: [
      { to: '/fleet', label: 'Fleet' },
      { to: '/groups', label: 'Groups' },
      { to: '/trips', label: 'Trips' },
      { to: '/maintenance', label: 'Maintenance' },
    ],
  },
  {
    label: 'Operations',
    items: [
      { to: '/fuel-expenses', label: 'Fuel & Expenses' },
      { to: '/analytics', label: 'Analytics' },
    ],
  },
  {
    label: 'Administration',
    items: [
      { to: '/settings', label: 'Settings' },
    ],
  },
];

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <BrowserRouter>
      <div className="app-layout">
        <aside className={`sidebar${sidebarOpen ? ' open' : ''}`}>
          <div className="sidebar-brand">
            <div className="sidebar-brand-icon">T</div>
            <div className="sidebar-brand-text">
              Transit<span>Ops</span>
            </div>
          </div>
          <nav className="sidebar-nav">
            {NAV_SECTIONS.map(section => (
              <div key={section.label}>
                <div className="sidebar-section-label">{section.label}</div>
                {section.items.map(item => (
                  <NavLink
                    key={item.to}
                    to={item.to}
                    className={({ isActive }) =>
                      `sidebar-link${isActive ? ' active' : ''}`
                    }
                    onClick={() => setSidebarOpen(false)}
                  >
                    {item.label}
                  </NavLink>
                ))}
              </div>
            ))}
          </nav>
          <div className="sidebar-footer">TransitOps v1.0</div>
        </aside>

        <div className="main-area">
          <header className="topbar">
            <div style={{ display: 'flex', alignItems: 'center', gap: 'var(--spacing-md)' }}>
              <button
                className="btn btn-ghost sidebar-toggle"
                onClick={() => setSidebarOpen(!sidebarOpen)}
              >
                ☰
              </button>
              <div className="topbar-search">
                <input type="text" placeholder="Search vehicles, logs..." />
              </div>
            </div>
            <div className="topbar-right">
              <span className="topbar-role">Fleet Manager</span>
              <div className="topbar-avatar">FM</div>
            </div>
          </header>

          <main className="page-content">
            <Routes>
              <Route path="/" element={<Navigate to="/dashboard" replace />} />
              <Route path="/dashboard" element={<DashboardPage />} />
              <Route path="/fleet" element={<FleetPage />} />
              <Route path="/maintenance" element={<MaintenancePage />} />
              <Route path="/groups" element={<PlaceholderPage title="Groups" />} />
              <Route path="/trips" element={<PlaceholderPage title="Trips" />} />
              <Route path="/fuel-expenses" element={<PlaceholderPage title="Fuel & Expenses" />} />
              <Route path="/analytics" element={<PlaceholderPage title="Analytics" />} />
              <Route path="/settings" element={<PlaceholderPage title="Settings" />} />
            </Routes>
          </main>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
