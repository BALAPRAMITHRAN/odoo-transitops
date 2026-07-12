-- =========================
-- ROLES
-- =========================
INSERT INTO roles (role_name, description) VALUES
('Fleet Manager', 'Manages fleet operations'),
('Driver', 'Assigned to drive vehicles'),
('Safety Officer', 'Monitors vehicle safety and compliance'),
('Financial Analyst', 'Tracks expenses and fuel costs');

-- =========================
-- USERS
-- =========================
INSERT INTO users (full_name, email, password_hash, role_id) VALUES
('Rahul Sharma', 'rahul@transitops.com', 'password123', 1),
('Amit Kumar', 'amit@transitops.com', 'password123', 2),
('Priya Nair', 'priya@transitops.com', 'password123', 3),
('Sneha Patel', 'sneha@transitops.com', 'password123', 4);

-- =========================
-- VEHICLES
-- =========================
INSERT INTO vehicles (
    registration_number,
    vehicle_name,
    vehicle_type,
    manufacturer,
    model_year,
    fuel_type,
    status,
    current_odometer
) VALUES
('TN01AB1234', 'Ashok Leyland Bus', 'Bus', 'Ashok Leyland', 2022, 'Diesel', 'AVAILABLE', 45210),
('TN09CD5678', 'Tata Truck', 'Truck', 'Tata', 2021, 'Diesel', 'IN_SERVICE', 68300),
('TN10EF9012', 'Mahindra Bolero', 'SUV', 'Mahindra', 2023, 'Diesel', 'AVAILABLE', 15890);

-- =========================
-- DRIVERS
-- =========================
INSERT INTO drivers (
    user_id,
    license_number,
    phone,
    experience_years,
    status
) VALUES
(2, 'DL1234567890', '9876543210', 6, 'ACTIVE');

-- =========================
-- TRIPS
-- =========================
INSERT INTO trips (
    vehicle_id,
    driver_id,
    start_location,
    destination,
    start_time,
    end_time,
    distance_km,
    trip_status
) VALUES
(
    1,
    1,
    'Chennai',
    'Vellore',
    '2026-07-12 08:00:00',
    '2026-07-12 11:00:00',
    140.50,
    'COMPLETED'
),
(
    2,
    1,
    'Chennai',
    'Coimbatore',
    '2026-07-13 06:30:00',
    NULL,
    505.00,
    'ONGOING'
);

-- =========================
-- MAINTENANCE LOGS
-- =========================
INSERT INTO maintenance_logs (
    vehicle_id,
    maintenance_type,
    service_date,
    next_service_date,
    cost,
    remarks
) VALUES
(
    1,
    'Engine Oil Change',
    '2026-06-20',
    '2026-09-20',
    5500.00,
    'Routine maintenance'
),
(
    2,
    'Brake Inspection',
    '2026-06-25',
    '2026-10-25',
    3200.00,
    'Brake pads replaced'
);

-- =========================
-- FUEL LOGS
-- =========================
INSERT INTO fuel_logs (
    vehicle_id,
    fuel_date,
    litres,
    cost,
    odometer_reading
) VALUES
(
    1,
    '2026-07-10',
    65.5,
    6200.00,
    45120
),
(
    2,
    '2026-07-11',
    120.0,
    11340.00,
    68150
);

-- =========================
-- EXPENSES
-- =========================
INSERT INTO expenses (
    vehicle_id,
    expense_type,
    amount,
    expense_date,
    description
) VALUES
(
    1,
    'Toll',
    450.00,
    '2026-07-12',
    'Chennai–Vellore Highway Toll'
),
(
    2,
    'Parking',
    300.00,
    '2026-07-13',
    'Warehouse parking charges'
);