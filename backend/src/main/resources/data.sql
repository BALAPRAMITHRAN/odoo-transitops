INSERT INTO vehicles (registration_number, name_model, vehicle_type, max_load_capacity, odometer, acquisition_cost, status, region)
SELECT 'TN01AB1234', 'Ashok Leyland Viking', 'Bus', 8000.00, 45210.00, 2500000.00, 'Available', 'Chennai'
WHERE NOT EXISTS (SELECT 1 FROM vehicles WHERE registration_number = 'TN01AB1234');

INSERT INTO vehicles (registration_number, name_model, vehicle_type, max_load_capacity, odometer, acquisition_cost, status, region)
SELECT 'TN09CD5678', 'Tata LPT 1613', 'Truck', 16000.00, 68300.00, 1800000.00, 'On Trip', 'Coimbatore'
WHERE NOT EXISTS (SELECT 1 FROM vehicles WHERE registration_number = 'TN09CD5678');

INSERT INTO vehicles (registration_number, name_model, vehicle_type, max_load_capacity, odometer, acquisition_cost, status, region)
SELECT 'TN10EF9012', 'Mahindra Bolero Pickup', 'Pickup', 1500.00, 15890.00, 850000.00, 'In Shop', 'Madurai'
WHERE NOT EXISTS (SELECT 1 FROM vehicles WHERE registration_number = 'TN10EF9012');

INSERT INTO vehicles (registration_number, name_model, vehicle_type, max_load_capacity, odometer, acquisition_cost, status, region)
SELECT 'KA04GH3456', 'Eicher Pro 3019', 'Truck', 19000.00, 120500.00, 2200000.00, 'Retired', 'Bangalore'
WHERE NOT EXISTS (SELECT 1 FROM vehicles WHERE registration_number = 'KA04GH3456');

INSERT INTO maintenance_logs (vehicle_id, description, cost, status, created_at)
SELECT v.id, 'Brake pad replacement', 5500.00, 'ACTIVE', now()
FROM vehicles v WHERE v.registration_number = 'TN10EF9012'
AND NOT EXISTS (SELECT 1 FROM maintenance_logs WHERE vehicle_id = v.id AND description = 'Brake pad replacement');
