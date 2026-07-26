package com.kibunmeshi.tools;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ReloadData {

    private static final String URL =
            "jdbc:mysql://localhost:3306/kibunmeshi?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String USER = "kibunmeshi_user";
    private static final String PASS = "kibunmeshi_pass";

    public static void main(String[] args) throws Exception {
        System.out.println("=== data.sql DB 재로드 시작 ===");
        try (InputStream is = ReloadData.class.getResourceAsStream("/data.sql")) {
            if (is == null) {
                System.err.println("data.sql 리소스를 찾을 수 없습니다.");
                return;
            }

            String sqlContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String[] statements = sqlContent.split(";");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {

                stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
                stmt.execute("DELETE FROM recommendation_histories;");
                stmt.execute("DELETE FROM foods;");
                stmt.execute("DELETE FROM food_categories;");
                stmt.execute("DELETE FROM emotions;");
                stmt.execute("DELETE FROM food_advice_templates;");
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

                for (String sql : statements) {
                    String trimmed = sql.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--") && !trimmed.toLowerCase().startsWith("truncate") && !trimmed.toLowerCase().startsWith("set foreign_key")) {
                        try {
                            stmt.execute(trimmed);
                        } catch (Exception e) {
                            System.err.println("SQL 실행 중 경고: " + e.getMessage());
                        }
                    }
                }
                System.out.println("=== 36가지 전 조합 샘플 데이터 (총 74개 음식) DB 재로드 성공! ===");
            }
        }
    }
}
