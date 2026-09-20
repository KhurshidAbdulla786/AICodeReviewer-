-- ============================================
-- AI Code Reviewer - Email Verification & Google OAuth
-- Version: 2.0
-- ============================================

-- ============================================
-- Email verification tokens table
-- ============================================
CREATE TABLE email_verification_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_email_verification_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- Add Google OAuth fields to users table
-- ============================================
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS google_id VARCHAR(255),
    ADD COLUMN IF NOT EXISTS oauth_provider VARCHAR(50),
    ADD COLUMN IF NOT EXISTS oauth_provider_id VARCHAR(255);

-- ============================================
-- Indexes
-- ============================================
CREATE INDEX idx_email_verification_token ON email_verification_tokens(token);
CREATE INDEX idx_email_verification_user ON email_verification_tokens(user_id);
CREATE INDEX idx_users_google_id ON users(google_id);
