-- ============================================================
-- マイグレーション: foods テーブルに effect / rarity カラム追加
-- 既存DB向け。新規セットアップは schema.sql を使用してください。
-- ============================================================

USE kibunmeshi;

ALTER TABLE foods
    ADD COLUMN effect VARCHAR(300) DEFAULT NULL COMMENT '栄養成分・効果' AFTER description,
    ADD COLUMN rarity VARCHAR(10) DEFAULT 'common' COMMENT 'レアリティ（common, rare, sr, ur）' AFTER effect;

-- 既存データ: effect が空なら description をコピー、rarity は common
UPDATE foods SET effect = description WHERE effect IS NULL AND description IS NOT NULL;
UPDATE foods SET rarity = 'common' WHERE rarity IS NULL OR rarity = '';

SELECT 'migration-v1-effect-rarity 完了' AS message;
