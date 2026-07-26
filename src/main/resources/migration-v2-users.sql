-- ============================================================
-- マイグレーション: users テーブル構造修正
-- password -> password_hash, role/enabled カラム追加
-- ============================================================

USE kibunmeshi;

-- password カラムを password_hash に変更
ALTER TABLE users 
    CHANGE COLUMN password password_hash VARCHAR(255) NOT NULL COMMENT 'パスワード（ハッシュ化）';

-- role カラム追加（既存ユーザーは USER に設定）
ALTER TABLE users 
    ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'USER' COMMENT 'ロール（USER, ADMIN）' AFTER email;

-- enabled カラム追加（既存ユーザーは TRUE に設定）
ALTER TABLE users 
    ADD COLUMN IF NOT EXISTS enabled BOOLEAN DEFAULT TRUE COMMENT '有効フラグ' AFTER role;

-- 既存データの role と enabled を設定
UPDATE users SET role = 'USER' WHERE role IS NULL;
UPDATE users SET enabled = TRUE WHERE enabled IS NULL;

SELECT 'migration-v2-users 完了' AS message;
