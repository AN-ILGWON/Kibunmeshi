-- ============================================================
-- きぶんめし (Kibunmeshi) データベーススキーマ (PostgreSQL)
-- ============================================================

-- 1. emotions
CREATE TABLE IF NOT EXISTS emotions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    label VARCHAR(20) NOT NULL,
    emoji VARCHAR(10) DEFAULT NULL,
    color_code VARCHAR(7) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_emotions_name ON emotions (name);

-- 2. food_categories
CREATE TABLE IF NOT EXISTS food_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    label VARCHAR(20) NOT NULL,
    icon VARCHAR(50) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_food_categories_name ON food_categories (name);

-- 3. foods
CREATE TABLE IF NOT EXISTS foods (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    emotion_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    description VARCHAR(200) DEFAULT NULL,
    effect VARCHAR(300) DEFAULT NULL,
    rarity VARCHAR(10) DEFAULT 'common',
    advice_type VARCHAR(20) DEFAULT 'general',
    image_keyword VARCHAR(50) DEFAULT NULL,
    image_url VARCHAR(500) DEFAULT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_food_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    CONSTRAINT fk_food_category FOREIGN KEY (category_id) REFERENCES food_categories(id)
);
CREATE INDEX IF NOT EXISTS idx_foods_emotion_category ON foods (emotion_id, category_id);
CREATE INDEX IF NOT EXISTS idx_foods_is_active ON foods (is_active);
CREATE INDEX IF NOT EXISTS idx_foods_advice_type ON foods (advice_type);

-- 4. food_advice_templates
CREATE TABLE IF NOT EXISTS food_advice_templates (
    id SERIAL PRIMARY KEY,
    emotion_id INTEGER DEFAULT NULL,
    advice_type VARCHAR(20) NOT NULL DEFAULT 'general',
    template_ja VARCHAR(300) NOT NULL,
    template_ko VARCHAR(300) DEFAULT NULL,
    gemini_prompt VARCHAR(500) DEFAULT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_template_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id)
);
CREATE INDEX IF NOT EXISTS idx_templates_emotion_type ON food_advice_templates (emotion_id, advice_type);
CREATE INDEX IF NOT EXISTS idx_templates_is_active ON food_advice_templates (is_active);

-- 5. users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) DEFAULT 'USER',
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_users_username ON users (username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);

-- 6. recommendation_histories
CREATE TABLE IF NOT EXISTS recommendation_histories (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    food_id INTEGER NOT NULL,
    emotion_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    is_favorite BOOLEAN DEFAULT FALSE,
    is_public BOOLEAN DEFAULT FALSE,
    advice_text VARCHAR(500) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_history_food FOREIGN KEY (food_id) REFERENCES foods(id),
    CONSTRAINT fk_history_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    CONSTRAINT fk_history_category FOREIGN KEY (category_id) REFERENCES food_categories(id)
);
CREATE INDEX IF NOT EXISTS idx_histories_user ON recommendation_histories (user_id);
CREATE INDEX IF NOT EXISTS idx_histories_food ON recommendation_histories (food_id);
CREATE INDEX IF NOT EXISTS idx_histories_created_at ON recommendation_histories (created_at);
CREATE INDEX IF NOT EXISTS idx_histories_is_public ON recommendation_histories (is_public);

-- 7. prompt_templates
CREATE TABLE IF NOT EXISTS prompt_templates (
    id SERIAL PRIMARY KEY,
    emotion VARCHAR(20) DEFAULT NULL,
    genre VARCHAR(20) DEFAULT NULL,
    template TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_prompt_emotion_genre ON prompt_templates (emotion, genre);

-- 8. recommendation_logs
CREATE TABLE IF NOT EXISTS recommendation_logs (
    id SERIAL PRIMARY KEY,
    emotion VARCHAR(20) NOT NULL,
    genre VARCHAR(20) NOT NULL,
    dish_name VARCHAR(100) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    image_prompt TEXT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_logs_created_at ON recommendation_logs (created_at);

-- View
CREATE OR REPLACE VIEW v_food_details AS
SELECT
    f.id AS food_id,
    f.name AS food_name,
    f.description,
    f.effect,
    f.rarity,
    f.advice_type,
    f.image_keyword,
    e.id AS emotion_id,
    e.name AS emotion_name,
    e.label AS emotion_label,
    e.emoji AS emotion_emoji,
    e.color_code AS emotion_color,
    c.id AS category_id,
    c.name AS category_name,
    c.label AS category_label,
    c.icon AS category_icon
FROM foods f
INNER JOIN emotions e ON f.emotion_id = e.id
INNER JOIN food_categories c ON f.category_id = c.id
WHERE f.is_active = TRUE;
