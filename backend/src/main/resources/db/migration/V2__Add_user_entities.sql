-- Migración V2: Añadir entidades de usuario y relacionadas
CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Actualizar tablas existentes para asociar a usuario
ALTER TABLE property ADD COLUMN IF NOT EXISTS user_id INTEGER REFERENCES "user"(id);
ALTER TABLE user_preference ADD COLUMN IF NOT EXISTS user_id INTEGER REFERENCES "user"(id) UNIQUE;
ALTER TABLE extraction_log ADD COLUMN IF NOT EXISTS user_id INTEGER REFERENCES "user"(id);

-- Nuevas tablas
CREATE TABLE favorite_property (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    property_id INTEGER NOT NULL REFERENCES property(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, property_id)
);

CREATE TABLE saved_search (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    filters_json TEXT NOT NULL, -- JSON con filtros
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE saved_comparison (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    property_ids INTEGER[] NOT NULL, -- Array de IDs de propiedades
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
