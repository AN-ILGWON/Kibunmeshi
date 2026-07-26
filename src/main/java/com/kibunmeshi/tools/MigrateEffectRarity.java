package com.kibunmeshi.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * foods テーブルに effect / rarity カラムを追加するワンショットマイグレーション。
 * 実行: ./gradlew migrateEffectRarity
 */
public class MigrateEffectRarity {

    private static final String URL =
            "jdbc:mysql://localhost:3306/kibunmeshi?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String USER = "kibunmeshi_user";
    private static final String PASS = "kibunmeshi_pass";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            if (!columnExists(conn, "foods", "effect")) {
                stmt.execute("""
                    ALTER TABLE foods
                        ADD COLUMN effect VARCHAR(300) DEFAULT NULL COMMENT '栄養成分・効果' AFTER description
                    """);
                System.out.println("Added column: foods.effect");
            } else {
                System.out.println("Skip: foods.effect already exists");
            }

            if (!columnExists(conn, "foods", "rarity")) {
                stmt.execute("""
                    ALTER TABLE foods
                        ADD COLUMN rarity VARCHAR(10) DEFAULT 'common' COMMENT 'レアリティ（common, rare, sr, ur）' AFTER effect
                    """);
                System.out.println("Added column: foods.rarity");
            } else {
                System.out.println("Skip: foods.rarity already exists");
            }

            if (!columnExists(conn, "foods", "image_url")) {
                stmt.execute("""
                    ALTER TABLE foods
                        ADD COLUMN image_url VARCHAR(500) DEFAULT NULL COMMENT '料理画像URL' AFTER image_keyword
                    """);
                System.out.println("Added column: foods.image_url");
            } else {
                System.out.println("Skip: foods.image_url already exists");
            }

            int effectUpdated = stmt.executeUpdate(
                    "UPDATE foods SET effect = description WHERE effect IS NULL AND description IS NOT NULL");
            int rarityUpdated = stmt.executeUpdate(
                    "UPDATE foods SET rarity = 'common' WHERE rarity IS NULL OR rarity = ''");

            applySeedRarities(stmt);

            System.out.println("Updated effect rows: " + effectUpdated);
            System.out.println("Updated rarity rows: " + rarityUpdated);
            System.out.println("migration-v1-effect-rarity complete");
        }
    }

    private static void applySeedRarities(Statement stmt) throws Exception {
        setRarity(stmt, "ビビンバ", "sr");
        setRarity(stmt, "小籠包", "sr");
        setRarity(stmt, "シリアル", "ur");
        setRarity(stmt, "サムゲタン", "rare");
        setRarity(stmt, "おでん", "rare");
        setRarity(stmt, "麻婆豆腐", "rare");
        setRarity(stmt, "杏仁豆腐", "rare");
        setRarity(stmt, "ハンバーグ", "rare");
    }

    private static void setRarity(Statement stmt, String name, String rarity) throws Exception {
        stmt.executeUpdate("UPDATE foods SET rarity = '" + rarity + "' WHERE name = '" + name + "'");
    }

    private static boolean columnExists(Connection conn, String table, String column) throws Exception {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("""
                 SELECT COUNT(*) AS cnt
                 FROM information_schema.COLUMNS
                 WHERE TABLE_SCHEMA = 'kibunmeshi'
                   AND TABLE_NAME = '%s'
                   AND COLUMN_NAME = '%s'
                 """.formatted(table, column))) {
            rs.next();
            return rs.getInt("cnt") > 0;
        }
    }
}
