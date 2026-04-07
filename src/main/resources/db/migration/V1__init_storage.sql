-- Core schema for CloudStore

-- Useful for gen_random_uuid() if you decide to default UUIDs at DB level.
-- CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================
-- Users & Auth
-- =========================

CREATE TABLE IF NOT EXISTS users (
    user_id         UUID PRIMARY KEY,
    email           TEXT NOT NULL UNIQUE,
    username        TEXT NOT NULL UNIQUE,
    hashed_password TEXT NOT NULL,
    role            VARCHAR(16) NOT NULL,
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL,
    version         BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS refresh_tokens (
    token_id   UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    jwt_id     TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked    BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS idempotency_keys (
    id              UUID PRIMARY KEY,
    idempotency_key TEXT NOT NULL UNIQUE,
    request_hash    TEXT NOT NULL,
    response_code   INTEGER,
    response_body   TEXT,
    status          VARCHAR(20) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_idem_created_at ON idempotency_keys(created_at);

-- =========================
-- Storage nodes
-- =========================

CREATE TABLE IF NOT EXISTS storage_nodes (
    id              UUID PRIMARY KEY,
    owner_id        UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    parent_id       UUID REFERENCES storage_nodes(id) ON DELETE SET NULL,
    name            TEXT NOT NULL,
    path            TEXT NOT NULL,
    type            VARCHAR(20) NOT NULL,

    object_key      TEXT,
    size_bytes      BIGINT,
    mime_type       TEXT,
    checksum_sha256 VARCHAR(64),

    is_trashed      BOOLEAN NOT NULL DEFAULT FALSE,
    trashed_at      TIMESTAMPTZ,

    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    version         BIGINT NOT NULL DEFAULT 0
);

-- Speed up folder browsing
CREATE INDEX IF NOT EXISTS idx_nodes_owner_parent_active ON storage_nodes(owner_id, parent_id) WHERE is_trashed = FALSE;
CREATE INDEX IF NOT EXISTS idx_nodes_owner_parent_trashed ON storage_nodes(owner_id, parent_id) WHERE is_trashed = TRUE;

-- Trash cleanup
CREATE INDEX IF NOT EXISTS idx_nodes_trashed_at ON storage_nodes(trashed_at) WHERE is_trashed = TRUE;

-- Name uniqueness within a folder for non-trashed nodes (case-insensitive)
-- Use a fixed UUID for NULL parent_id so root-level names are also unique.
CREATE UNIQUE INDEX IF NOT EXISTS ux_nodes_owner_parent_name_active
    ON storage_nodes (
        owner_id,
        COALESCE(parent_id, '00000000-0000-0000-0000-000000000000'::uuid),
        LOWER(name)
    )
    WHERE is_trashed = FALSE;

-- =========================
-- File versions
-- =========================

CREATE TABLE IF NOT EXISTS file_versions (
    id              UUID PRIMARY KEY,
    node_id         UUID NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
    version_no      INTEGER NOT NULL,
    object_key      TEXT NOT NULL,
    size_bytes      BIGINT NOT NULL,
    mime_type       TEXT,
    checksum_sha256 VARCHAR(64),
    created_at      TIMESTAMPTZ NOT NULL,
    CONSTRAINT ux_file_versions_node_version UNIQUE(node_id, version_no)
);

-- =========================
-- Share links
-- =========================

CREATE TABLE IF NOT EXISTS share_links (
    id             UUID PRIMARY KEY,
    node_id        UUID NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
    token          TEXT NOT NULL UNIQUE,
    created_at     TIMESTAMP NOT NULL,
    expires_at     TIMESTAMP,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    max_downloads  INTEGER,
    download_count INTEGER NOT NULL DEFAULT 0,
    password_hash  TEXT
);

CREATE INDEX IF NOT EXISTS idx_share_node ON share_links(node_id);

-- =========================
-- Audit events
-- =========================

CREATE TABLE IF NOT EXISTS audit_events (
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    action     VARCHAR(64) NOT NULL,
    node_id    UUID,
    metadata   JSONB,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_audit_user_time ON audit_events(user_id, created_at DESC);