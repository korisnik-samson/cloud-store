CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- users table: adapt this to your current schema if already created
-- IMPORTANT: fix UUID column type consistency in your existing Users entity first.

CREATE TABLE IF NOT EXISTS storage_nodes (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    mime_type VARCHAR(255),
    size_bytes BIGINT,
    object_key VARCHAR(512),
    checksum_sha256 VARCHAR(64),
    parent_id UUID NULL REFERENCES storage_nodes(id) ON DELETE SET NULL,
    path VARCHAR(1024) NOT NULL DEFAULT '/',
    is_trashed BOOLEAN NOT NULL DEFAULT FALSE,
    trashed_at TIMESTAMPTZ NULL,
    owner_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_node_owner_parent ON storage_nodes(owner_id, parent_id);
CREATE INDEX IF NOT EXISTS idx_node_owner_name ON storage_nodes(owner_id, name);
CREATE INDEX IF NOT EXISTS idx_node_path ON storage_nodes(path);

ALTER TABLE file_meta_data
    ADD COLUMN IF NOT EXISTS is_trashed BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE file_meta_data
    ADD COLUMN IF NOT EXISTS trashed_at TIMESTAMPTZ NULL;

CREATE TABLE IF NOT EXISTS share_links (
    id UUID PRIMARY KEY,
    node_id UUID NOT NULL REFERENCES file_meta_data(id) ON DELETE CASCADE,
    token VARCHAR(128) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    expires_at TIMESTAMPTZ,
    max_downloads INT,
    download_count INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_share_token ON share_links(token);