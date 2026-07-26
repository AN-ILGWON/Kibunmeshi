package com.kibunmeshi.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * users テーブル構造修正ワンショットマイグレーション
 * password -> password_hash, role/enabled カラム追加
 * 実行: ./gradlew migrateUsers
 */
public class MigrateUsers {

    private static final String URL =
            "jdbc:mysql://localhost:3306/kibunmeshi?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String USER = "kibunmeshi_user";
    private static final String PASS = "kibunmeshi_pass";

    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // password 컬럼을 password_hash로 변경
            if (columnExists(conn, "users", "password") && !columnExists(conn, "users", "password_hash")) {
                stmt.execute("""
                    ALTER TABLE users
                        CHANGE COLUMN password password_hash VARCHAR(255) NOT NULL COMMENT 'パスワード（ハッシュ化）'
                    """);
                System.out.println("Renamed column: users.password -> users.password_hash");
            } else if (columnExists(conn, "users", "password_hash")) {
                System.out.println("Skip: users.password_hash already exists");
            } else {
                System.out.println("Warning: users.password column not found");
            }

            // role 컬럼 추가
            if (!columnExists(conn, "users", "role")) {
                stmt.execute("""
                    ALTER TABLE users
                        ADD COLUMN role VARCHAR(20) DEFAULT 'USER' COMMENT 'ロール（USER, ADMIN）' AFTER email
                    """);
                System.out.println("Added column: users.role");
            } else {
                System.out.println("Skip: users.role already exists");
            }

            // enabled 컬럼 추가
            if (!columnExists(conn, "users", "enabled")) {
                stmt.execute("""
                    ALTER TABLE users
                        ADD COLUMN enabled BOOLEAN DEFAULT TRUE COMMENT '有効フラグ' AFTER role
                    """);
                System.out.println("Added column: users.enabled");
            } else {
                System.out.println("Skip: users.enabled already exists");
            }

            // 既存データの role と enabled を設定
            int roleUpdated = stmt.executeUpdate(
                    "UPDATE users SET role = 'USER' WHERE role IS NULL");
            int enabledUpdated = stmt.executeUpdate(
                    "UPDATE users SET enabled = TRUE WHERE enabled IS NULL");

            System.out.println("Updated role rows: " + roleUpdated);
            System.out.println("Updated enabled rows: " + enabledUpdated);
            System.out.println("migration-v2-users complete");
        }
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
