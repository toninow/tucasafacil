-- Migración inicial: Crear tablas principales
CREATE TABLE source_portal (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    domain VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE property (
    id SERIAL PRIMARY KEY,
    source_portal_id INTEGER REFERENCES source_portal(id),
    source_url TEXT NOT NULL UNIQUE,
    title VARCHAR(255),
    description TEXT,
    price DECIMAL(10,2),
    currency VARCHAR(3) DEFAULT 'EUR',
    address TEXT,
    district VARCHAR(100),
    neighborhood VARCHAR(100),
    city VARCHAR(100) DEFAULT 'Madrid',
    province VARCHAR(100) DEFAULT 'Madrid',
    postal_code VARCHAR(10),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    square_meters INTEGER,
    bedrooms INTEGER,
    bathrooms INTEGER,
    floor INTEGER,
    has_elevator BOOLEAN DEFAULT FALSE,
    furnished BOOLEAN DEFAULT FALSE,
    has_terrace BOOLEAN DEFAULT FALSE,
    has_balcony BOOLEAN DEFAULT FALSE,
    has_garage BOOLEAN DEFAULT FALSE,
    has_storage_room BOOLEAN DEFAULT FALSE,
    expenses_included BOOLEAN DEFAULT FALSE,
    listing_type VARCHAR(50), -- alquiler, venta, etc.
    advertiser_type VARCHAR(50), -- agencia, particular
    score DECIMAL(5,2),
    housing_score DECIMAL(5,2),
    area_score DECIMAL(5,2),
    requirement_score DECIMAL(5,2),
    extracted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE property_requirement (
    id SERIAL PRIMARY KEY,
    property_id INTEGER REFERENCES property(id) ON DELETE CASCADE,
    requires_permanent_contract BOOLEAN DEFAULT FALSE,
    required_payslips INTEGER,
    deposit_months INTEGER,
    advance_months INTEGER,
    requires_guarantor BOOLEAN DEFAULT FALSE,
    requires_rent_default_insurance BOOLEAN DEFAULT FALSE,
    estimated_minimum_income DECIMAL(10,2),
    pets_allowed BOOLEAN DEFAULT TRUE,
    preferred_profile VARCHAR(100),
    max_occupants INTEGER,
    notes TEXT
);

CREATE TABLE zone_analysis (
    id SERIAL PRIMARY KEY,
    property_id INTEGER REFERENCES property(id) ON DELETE CASCADE,
    nearest_metro_distance_minutes INTEGER,
    nearest_bus_distance_minutes INTEGER,
    supermarket_count_500m INTEGER DEFAULT 0,
    pharmacy_count_500m INTEGER DEFAULT 0,
    gym_count_1000m INTEGER DEFAULT 0,
    park_count_1000m INTEGER DEFAULT 0,
    restaurant_count_1000m INTEGER DEFAULT 0,
    health_center_count_2000m INTEGER DEFAULT 0,
    shopping_center_count_3000m INTEGER DEFAULT 0,
    entertainment_score DECIMAL(3,1) DEFAULT 0,
    transport_score DECIMAL(3,1) DEFAULT 0,
    service_score DECIMAL(3,1) DEFAULT 0,
    zone_summary TEXT
);

CREATE TABLE property_image (
    id SERIAL PRIMARY KEY,
    property_id INTEGER REFERENCES property(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    sort_order INTEGER DEFAULT 0
);

CREATE TABLE user_preference (
    id SERIAL PRIMARY KEY,
    max_budget DECIMAL(10,2),
    preferred_zones TEXT, -- JSON array
    min_bedrooms INTEGER,
    max_deposit_months INTEGER,
    prefer_private_owner BOOLEAN DEFAULT FALSE,
    require_good_transport BOOLEAN DEFAULT FALSE,
    require_supermarket_nearby BOOLEAN DEFAULT FALSE,
    require_gym_nearby BOOLEAN DEFAULT FALSE,
    value_entertainment BOOLEAN DEFAULT FALSE,
    accepts_guarantor BOOLEAN DEFAULT TRUE,
    accepts_insurance BOOLEAN DEFAULT TRUE,
    reference_latitude DECIMAL(9,6),
    reference_longitude DECIMAL(9,6),
    scoring_weights_json TEXT -- JSON con pesos
);

CREATE TABLE extraction_log (
    id SERIAL PRIMARY KEY,
    source_url TEXT NOT NULL,
    source_portal VARCHAR(50),
    status VARCHAR(20) NOT NULL, -- SUCCESS, ERROR, etc.
    message TEXT,
    raw_data_snippet TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar portales iniciales
INSERT INTO source_portal (name, domain) VALUES ('Idealista', 'idealista.com');
INSERT INTO source_portal (name, domain) VALUES ('Fotocasa', 'fotocasa.es');
INSERT INTO source_portal (name, domain) VALUES ('Genérico', 'generic');