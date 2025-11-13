-- Car and Bike Rental Management System - Supabase PostgreSQL Setup
-- Run this in Supabase SQL Editor

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS public.bookings CASCADE;
DROP TABLE IF EXISTS public.vehicles CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

-- Table: users
-- Stores customer and admin information
CREATE TABLE public.users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(20) NOT NULL  -- 'CUSTOMER' or 'ADMIN'
);

-- Table: vehicles
-- Stores car and bike inventory
CREATE TABLE public.vehicles (
  id SERIAL PRIMARY KEY,
  type VARCHAR(10) NOT NULL,  -- 'CAR' or 'BIKE'
  brand VARCHAR(50) NOT NULL,
  model VARCHAR(50) NOT NULL,
  reg_number VARCHAR(50) UNIQUE NOT NULL,
  price_per_day NUMERIC(10,2) NOT NULL,
  available BOOLEAN NOT NULL DEFAULT TRUE
);

-- Table: bookings
-- Stores rental reservations
CREATE TABLE public.bookings (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES public.users(id),
  vehicle_id INT NOT NULL REFERENCES public.vehicles(id),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  total_price NUMERIC(10,2) NOT NULL,
  status VARCHAR(20) NOT NULL  -- 'BOOKED', 'CANCELLED', 'COMPLETED'
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON public.users(email);
CREATE INDEX idx_vehicles_type ON public.vehicles(type);
CREATE INDEX idx_vehicles_available ON public.vehicles(available);
CREATE INDEX idx_bookings_user ON public.bookings(user_id);
CREATE INDEX idx_bookings_vehicle ON public.bookings(vehicle_id);

-- Insert demo users
INSERT INTO public.users (name, email, password, role) VALUES
  ('Admin', 'admin@rental.com', 'admin123', 'ADMIN'),
  ('Joyce', 'joyce@demo.com', 'password', 'CUSTOMER'),
  ('John Doe', 'john@demo.com', 'pass123', 'CUSTOMER');

-- Insert demo vehicles
INSERT INTO public.vehicles (type, brand, model, reg_number, price_per_day, available) VALUES
  ('CAR', 'Toyota', 'Innova', 'KA01AB1234', 2000.00, TRUE),
  ('CAR', 'Honda', 'City', 'KA02CD5678', 1500.00, TRUE),
  ('CAR', 'Hyundai', 'Creta', 'KA03EF9012', 1800.00, TRUE),
  ('BIKE', 'Yamaha', 'FZ', 'KA02XY5678', 700.00, TRUE),
  ('BIKE', 'Honda', 'CBR', 'KA04GH3456', 900.00, TRUE),
  ('BIKE', 'Royal Enfield', 'Classic 350', 'KA05IJ7890', 800.00, TRUE);

-- Verify tables were created
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;

-- Verify data
SELECT 'Users' as table_name, COUNT(*) as row_count FROM public.users
UNION ALL
SELECT 'Vehicles', COUNT(*) FROM public.vehicles
UNION ALL
SELECT 'Bookings', COUNT(*) FROM public.bookings;
