-- Ajustes de esquema para que coincida con entidades/DTOs actuales
ALTER TABLE saved_search ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE saved_search ADD COLUMN IF NOT EXISTS last_executed_at TIMESTAMP;

ALTER TABLE saved_comparison ADD COLUMN IF NOT EXISTS description TEXT;
