package com.kibunmeshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * きぶんめしアプリケーションのエントリーポイント
 * 
 * 気分に合わせて料理を推薦するWebアプリケーション
 * Spring Boot 3.x + Thymeleaf + MyBatis + MySQL
 * 
 * @author 作者名
 * @version 1.0.0
 */
@SpringBootApplication
public class KibunmeshiApplication {

    /**
     * アプリケーションのメインメソッド
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(KibunmeshiApplication.class, args);
        
        // 起動完了ログ
        System.out.println("========================================");
        System.out.println("  きぶんめし (Kibunmeshi) 起動完了！");
        System.out.println("  http://localhost:8080");
        System.out.println("========================================");
    }
}
