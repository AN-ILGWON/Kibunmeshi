-- ============================================================
-- きぶんめし (Kibunmeshi) データベーススキーマ
-- 気分に合わせて料理を推薦するWebアプリケーション
-- 
-- 対象DB: MySQL 8.0
-- 文字コード: utf8mb4
-- ============================================================

-- データベース作成（存在しない場合）
CREATE DATABASE IF NOT EXISTS kibunmeshi 
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE kibunmeshi;

-- ============================================================
-- 1. emotions（気分マスター）
-- ユーザーが選択できる6種類の気分
-- ============================================================
CREATE TABLE IF NOT EXISTS emotions (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '気分ID',
    name VARCHAR(20) NOT NULL UNIQUE COMMENT '英語名（joy, sad, angry...）',
    label VARCHAR(20) NOT NULL COMMENT '日本語ラベル（うれしい, かなしい...）',
    emoji VARCHAR(10) DEFAULT NULL COMMENT '絵文字（😊, 😢...）',
    color_code VARCHAR(7) DEFAULT NULL COMMENT '色コード（#FFD700...）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='気分マスター';

-- ============================================================
-- 2. food_categories（料理カテゴリマスター）
-- 韓国料理、洋食、中華、和食、その他
-- ============================================================
CREATE TABLE IF NOT EXISTS food_categories (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'カテゴリID',
    name VARCHAR(20) NOT NULL UNIQUE COMMENT '英語名（korean, western...）',
    label VARCHAR(20) NOT NULL COMMENT '日本語ラベル（韓国料理, 洋食...）',
    icon VARCHAR(50) DEFAULT NULL COMMENT 'アイコン絵文字（🥘, 🍔...）',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='料理カテゴリマスター';

-- ============================================================
-- 3. foods（料理データ）
-- 気分×カテゴリの組み合わせで推薦される料理
-- ============================================================
CREATE TABLE IF NOT EXISTS foods (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '料理ID',
    name VARCHAR(50) NOT NULL COMMENT '料理名（サムゲタン, ステーキ...）',
    emotion_id INT NOT NULL COMMENT '対応気分ID',
    category_id INT NOT NULL COMMENT 'カテゴリID',
    description VARCHAR(200) DEFAULT NULL COMMENT '料理説明',
    effect VARCHAR(300) DEFAULT NULL COMMENT '栄養成分・効果',
    rarity VARCHAR(10) DEFAULT 'common' COMMENT 'レアリティ（common, rare, sr, ur）',
    advice_type VARCHAR(20) DEFAULT 'general' COMMENT 'アドバイスタイプ（celebrate, gentle...）',
    image_keyword VARCHAR(50) DEFAULT NULL COMMENT 'Unsplash検索キーワード',
    image_url VARCHAR(500) DEFAULT NULL COMMENT '料理画像URL',
    is_active BOOLEAN DEFAULT TRUE COMMENT '有効フラグ',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    
    -- 外部キー制約
    CONSTRAINT fk_food_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    CONSTRAINT fk_food_category FOREIGN KEY (category_id) REFERENCES food_categories(id),
    
    -- インデックス
    INDEX idx_emotion_category (emotion_id, category_id),
    INDEX idx_is_active (is_active),
    INDEX idx_advice_type (advice_type)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='料理データ';

-- ============================================================
-- 4. food_advice_templates（アドバイステンプレート）
-- Gemini連携用のテンプレート管理
-- ============================================================
CREATE TABLE IF NOT EXISTS food_advice_templates (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'テンプレートID',
    emotion_id INT DEFAULT NULL COMMENT '対応気分ID（NULL=汎用）',
    advice_type VARCHAR(20) NOT NULL DEFAULT 'general' COMMENT 'アドバイスタイプ',
    template_ja VARCHAR(300) NOT NULL COMMENT '日本語テンプレート',
    template_ko VARCHAR(300) DEFAULT NULL COMMENT '韓国語テンプレート（オプション）',
    gemini_prompt VARCHAR(500) DEFAULT NULL COMMENT 'Gemini用追加プロンプト',
    is_active BOOLEAN DEFAULT TRUE COMMENT '有効フラグ',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    
    -- 外部キー制約
    CONSTRAINT fk_template_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    
    -- インデックス
    INDEX idx_emotion_type (emotion_id, advice_type),
    INDEX idx_is_active (is_active)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='アドバイステンプレート';

-- ============================================================
-- 5. users（ユーザー）
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザーID',
    username VARCHAR(30) NOT NULL UNIQUE COMMENT 'ログインID',
    password_hash VARCHAR(255) NOT NULL COMMENT 'パスワード（ハッシュ化）',
    nickname VARCHAR(30) NOT NULL COMMENT 'ニックネーム',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'メールアドレス',
    role VARCHAR(20) DEFAULT 'USER' COMMENT 'ロール（USER, ADMIN）',
    enabled BOOLEAN DEFAULT TRUE COMMENT '有効フラグ',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    
    INDEX idx_username (username),
    INDEX idx_email (email)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ユーザー';

-- ============================================================
-- 6. recommendation_histories（推薦履歴）
-- ユーザーの推薦履歴を保存
-- ============================================================
CREATE TABLE IF NOT EXISTS recommendation_histories (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '履歴ID',
    user_id INT NOT NULL COMMENT 'ユーザーID',
    food_id INT NOT NULL COMMENT '推薦された料理ID',
    emotion_id INT NOT NULL COMMENT '選択した気分ID',
    category_id INT NOT NULL COMMENT '選択したカテゴリID',
    is_favorite BOOLEAN DEFAULT FALSE COMMENT 'お気に入りフラグ',
    is_public BOOLEAN DEFAULT FALSE COMMENT '公開フラグ',
    advice_text VARCHAR(500) DEFAULT NULL COMMENT 'アドバイステキスト',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '推薦日時',
    
    -- 外部キー制約
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_history_food FOREIGN KEY (food_id) REFERENCES foods(id),
    CONSTRAINT fk_history_emotion FOREIGN KEY (emotion_id) REFERENCES emotions(id),
    CONSTRAINT fk_history_category FOREIGN KEY (category_id) REFERENCES food_categories(id),
    
    -- インデックス
    INDEX idx_user (user_id),
    INDEX idx_food (food_id),
    INDEX idx_created_at (created_at),
    INDEX idx_is_public (is_public)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='推薦履歴';

-- ============================================================
-- 7. prompt_templates（AI 프롬프트 템플릿）
-- 감정과 장르에 따른 AI 추천 프롬프트 템플릿
-- ============================================================
CREATE TABLE IF NOT EXISTS prompt_templates (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '템플릿ID',
    emotion VARCHAR(20) DEFAULT NULL COMMENT '감정 (joy, sad, angry...)',
    genre VARCHAR(20) DEFAULT NULL COMMENT '장르 (korean, western...)',
    template TEXT NOT NULL COMMENT '프롬프트 템플릿',
    is_active BOOLEAN DEFAULT TRUE COMMENT '활성 여부',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    
    INDEX idx_emotion_genre (emotion, genre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 프롬프트 템플릿';

-- ============================================================
-- 8. recommendation_logs（추천 요청 로그）
-- 추천 요청 및 결과 저장
-- ============================================================
CREATE TABLE IF NOT EXISTS recommendation_logs (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '로그ID',
    emotion VARCHAR(20) NOT NULL COMMENT '감정',
    genre VARCHAR(20) NOT NULL COMMENT '장르',
    dish_name VARCHAR(100) DEFAULT NULL COMMENT '추천 요리명',
    description TEXT DEFAULT NULL COMMENT '설명',
    image_prompt TEXT DEFAULT NULL COMMENT '이미지 프롬프트',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='추천 요청 로그';

-- ============================================================
-- 初期データ投入確認用ビュー（オプション）
-- ============================================================
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

-- ============================================================
-- データベース初期化完了コメント
-- ============================================================
SELECT 'きぶんめし (Kibunmeshi) データベース初期化完了' AS message;
