CREATE TABLE IF NOT EXISTS profiles
(
    id               BIGSERIAL    NOT NULL,
    session_id       VARCHAR      NOT NULL UNIQUE PRIMARY KEY,
    display_name     VARCHAR(255) NOT NULL,
    birthday         DATE         NOT NULL,
    description      TEXT,
    is_deleted       BOOL         NOT NULL DEFAULT false,
    created_at       TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NULL,
    last_online      TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);
