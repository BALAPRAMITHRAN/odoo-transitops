CREATE TABLE users(...);

CREATE TABLE roles(...);

CREATE TABLE vehicles(...);

CREATE TABLE drivers(...);

CREATE TABLE trips(...);

CREATE TABLE maintenance_logs(...);

CREATE TABLE fuel_logs(...);

CREATE TABLE expenses(...);
-- =========================
-- ROLES
-- =========================
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    role_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
);

-- =========================
-- VEHICLES
-- =========================
CREATE TABLE vehicles (
    id SERIAL PRIMARY KEY,
    registration_number VARCHAR(30) UNIQUE NOT NULL,
    vehicle_name VARCHAR(100),
    vehicle_type VARCHAR(50),
    manufacturer VARCHAR(50),
    model_year INT,
    fuel_type VARCHAR(20),
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    current_odometer INT DEFAULT 0
);

-- =========================
-- DRIVERS
-- =========================
CREATE TABLE drivers (
    id SERIAL PRIMARY KEY,
    user_id INT,
    license_number VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(20),
    experience_years INT,
    status VARCHAR(20) DEFAULT 'ACTIVE',

    CONSTRAINT fk_driver_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- =========================
-- TRIPS
-- =========================
CREATE TABLE trips (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    driver_id INT NOT NULL,

    start_location VARCHAR(100),
    destination VARCHAR(100),

    start_time TIMESTAMP,
    end_time TIMESTAMP,

    distance_km DECIMAL(10,2),
    trip_status VARCHAR(30) DEFAULT 'PLANNED',

    CONSTRAINT fk_trip_vehicle
        FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(id),

    CONSTRAINT fk_trip_driver
        FOREIGN KEY (driver_id)
        REFERENCES drivers(id)
);

-- =========================
-- MAINTENANCE LOGS
-- =========================
CREATE TABLE maintenance_logs (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    maintenance_type VARCHAR(100),
    service_date DATE,
    next_service_date DATE,
    cost DECIMAL(10,2),
    remarks TEXT,

    CONSTRAINT fk_maintenance_vehicle
        FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(id)
);

-- =========================
-- FUEL LOGS
-- =========================
CREATE TABLE fuel_logs (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    fuel_date DATE,
    litres DECIMAL(8,2),
    cost DECIMAL(10,2),
    odometer_reading INT,

    CONSTRAINT fk_fuel_vehicle
        FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(id)
);

-- =========================
-- EXPENSES
-- =========================
CREATE TABLE expenses (
    id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    expense_type VARCHAR(50),
    amount DECIMAL(10,2),
    expense_date DATE,
    description TEXT,

    CONSTRAINT fk_expense_vehicle
        FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(id)
);