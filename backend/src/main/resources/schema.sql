CREATE TABLE IF NOT EXISTS vehicles (
  id SERIAL PRIMARY KEY,
  registration_number VARCHAR(30) UNIQUE NOT NULL,
  name_model VARCHAR(100),
  vehicle_type VARCHAR(50),
  max_load_capacity NUMERIC(10,2),
  odometer NUMERIC(10,2) DEFAULT 0,
  acquisition_cost NUMERIC(12,2),
  status VARCHAR(20) DEFAULT 'Available',
  region VARCHAR(50),
  health_score INT DEFAULT 100,
  priority VARCHAR(20) DEFAULT 'Medium'
);

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'vehicles' AND column_name = 'health_score') THEN
    ALTER TABLE vehicles ADD COLUMN health_score INT DEFAULT 100;
  END IF;
  IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'vehicles' AND column_name = 'priority') THEN
    ALTER TABLE vehicles ADD COLUMN priority VARCHAR(20) DEFAULT 'Medium';
  END IF;
  IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'maintenance_logs' AND column_name = 'odometer_at_service') THEN
    ALTER TABLE maintenance_logs ADD COLUMN odometer_at_service NUMERIC(10,2);
  END IF;
END $$;

CREATE TABLE IF NOT EXISTS maintenance_logs (
  id SERIAL PRIMARY KEY,
  vehicle_id INT REFERENCES vehicles(id),
  description VARCHAR(200),
  cost NUMERIC(10,2),
  status VARCHAR(20) DEFAULT 'ACTIVE',
  created_at TIMESTAMP DEFAULT now(),
  closed_at TIMESTAMP,
  odometer_at_service NUMERIC(10,2)
);
