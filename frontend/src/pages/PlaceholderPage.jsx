function PlaceholderPage({ title }) {
  return (
    <div>
      <div className="page-header">
        <div className="page-header-left">
          <h1 className="page-title">{title}</h1>
          <p className="page-subtitle">Module under development</p>
        </div>
      </div>
      <div className="placeholder-page">
        <h2 className="placeholder-title">Coming Soon</h2>
        <p className="placeholder-text">
          This module is being built by a teammate and will be available shortly.
        </p>
      </div>
    </div>
  );
}

export default PlaceholderPage;
