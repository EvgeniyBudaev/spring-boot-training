CREATE TABLE IF NOT EXISTS catalogs
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    description      TEXT,
    created_by       UUID NOT NULL, -- Keycloak User ID
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP  NULL
);