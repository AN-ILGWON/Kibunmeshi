-- ============================================================
-- きぶんめし (Kibunmeshi) マイグレーション
-- foods 테이블에 effect, rarity 컬럼 추가
-- ============================================================

USE kibunmeshi;

-- effect 컬럼 추가 (이미 존재하면 무시)
ALTER TABLE foods 
ADD COLUMN IF NOT EXISTS effect VARCHAR(300) DEFAULT NULL COMMENT '栄養成分・効果';

-- rarity 컬럼 추가 (이미 존재하면 무시)
ALTER TABLE foods 
ADD COLUMN IF NOT EXISTS rarity VARCHAR(10) DEFAULT 'common' COMMENT 'レアリティ（common, rare, sr, ur）';

-- image_url 컬럼 추가 (이미 존재하면 무시)
ALTER TABLE foods 
ADD COLUMN IF NOT EXISTS image_url VARCHAR(500) DEFAULT NULL COMMENT '料理画像URL';
