-- ============================================
-- AI Code Reviewer - Initial Database Schema
-- Version: 1.0
-- ============================================

-- ENUM types
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');
CREATE TYPE review_status AS ENUM ('PENDING', 'COMPLETED', 'FAILED');
CREATE TYPE language_type AS ENUM ('JAVA', 'PYTHON', 'JAVASCRIPT', 'TYPESCRIPT', 'CPP', 'CSHARP', 'GO');

-- ============================================
-- Users table
-- ============================================
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    avatar_url VARCHAR(500),
    role user_role NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_username UNIQUE (username),
    CONSTRAINT uk_users_email UNIQUE (email)
);

-- ============================================
-- Refresh tokens table
-- ============================================
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- Projects table
-- ============================================
CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description VARCHAR(1000),
    language language_type NOT NULL DEFAULT 'JAVA',
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_projects_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- Source files table
-- ============================================
CREATE TABLE source_files (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    filename VARCHAR(255) NOT NULL,
    file_path VARCHAR(1000),
    content TEXT NOT NULL,
    language language_type NOT NULL,
    file_size BIGINT NOT NULL DEFAULT 0,
    checksum VARCHAR(64),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_source_files_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

-- ============================================
-- Reviews table
-- ============================================
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    source_file_id BIGINT NOT NULL,
    status review_status NOT NULL DEFAULT 'PENDING',
    overall_score INTEGER CHECK (overall_score >= 0 AND overall_score <= 100),
    readability_score INTEGER CHECK (readability_score >= 0 AND readability_score <= 100),
    naming_score INTEGER CHECK (naming_score >= 0 AND naming_score <= 100),
    style_score INTEGER CHECK (style_score >= 0 AND style_score <= 100),
    solid_score INTEGER CHECK (solid_score >= 0 AND solid_score <= 100),
    oop_score INTEGER CHECK (oop_score >= 0 AND oop_score <= 100),
    duplicate_code_score INTEGER CHECK (duplicate_code_score >= 0 AND duplicate_code_score <= 100),
    dead_code_score INTEGER CHECK (dead_code_score >= 0 AND dead_code_score <= 100),
    exception_handling_score INTEGER CHECK (exception_handling_score >= 0 AND exception_handling_score <= 100),
    security_score INTEGER CHECK (security_score >= 0 AND security_score <= 100),
    performance_score INTEGER CHECK (performance_score >= 0 AND performance_score <= 100),
    strengths TEXT,
    weaknesses TEXT,
    suggestions TEXT,
    cyclomatic_complexity INTEGER,
    lines_of_code INTEGER,
    number_of_classes INTEGER,
    number_of_methods INTEGER,
    time_complexity VARCHAR(50),
    space_complexity VARCHAR(50),
    maintainability_score INTEGER CHECK (maintainability_score >= 0 AND maintainability_score <= 100),
    ai_response_json JSONB,
    ai_model_used VARCHAR(50),
    processing_time_ms BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_reviews_source_file FOREIGN KEY (source_file_id) REFERENCES source_files(id) ON DELETE CASCADE
);

-- ============================================
-- Review suggestions table
-- ============================================
CREATE TABLE review_suggestions (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL,
    category VARCHAR(50) NOT NULL,
    severity VARCHAR(20) NOT NULL DEFAULT 'INFO',
    line_start INTEGER,
    line_end INTEGER,
    message TEXT NOT NULL,
    original_code TEXT,
    suggested_code TEXT,
    explanation TEXT,
    is_applied BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_suggestions_review FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE
);

-- ============================================
-- Bug detections table
-- ============================================
CREATE TABLE bug_detections (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL,
    bug_type VARCHAR(50) NOT NULL,
    severity VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    line_number INTEGER,
    description TEXT NOT NULL,
    code_snippet TEXT,
    recommendation TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bug_detections_review FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE
);

-- ============================================
-- AI conversations table
-- ============================================
CREATE TABLE ai_conversations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    review_id BIGINT,
    source_file_id BIGINT,
    user_message TEXT NOT NULL,
    ai_response TEXT NOT NULL,
    message_type VARCHAR(50) NOT NULL DEFAULT 'CHAT',
    tokens_used INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ai_conversations_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_ai_conversations_review FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE SET NULL,
    CONSTRAINT fk_ai_conversations_source_file FOREIGN KEY (source_file_id) REFERENCES source_files(id) ON DELETE SET NULL
);

-- ============================================
-- API usage tracking table
-- ============================================
CREATE TABLE api_usage_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL,
    status_code INTEGER NOT NULL,
    response_time_ms BIGINT,
    ip_address VARCHAR(45),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_api_usage_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================
-- Indexes for performance
-- ============================================
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_refresh_tokens_user ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_token ON refresh_tokens(token);
CREATE INDEX idx_projects_user ON projects(user_id);
CREATE INDEX idx_projects_created ON projects(created_at DESC);
CREATE INDEX idx_source_files_project ON source_files(project_id);
CREATE INDEX idx_reviews_user ON reviews(user_id);
CREATE INDEX idx_reviews_source_file ON reviews(source_file_id);
CREATE INDEX idx_reviews_created ON reviews(created_at DESC);
CREATE INDEX idx_reviews_status ON reviews(status);
CREATE INDEX idx_suggestions_review ON review_suggestions(review_id);
CREATE INDEX idx_bug_detections_review ON bug_detections(review_id);
CREATE INDEX idx_ai_conversations_user ON ai_conversations(user_id);
CREATE INDEX idx_ai_conversations_created ON ai_conversations(created_at DESC);
CREATE INDEX idx_api_usage_user_date ON api_usage_logs(user_id, created_at DESC);
CREATE INDEX idx_api_usage_endpoint ON api_usage_logs(endpoint);