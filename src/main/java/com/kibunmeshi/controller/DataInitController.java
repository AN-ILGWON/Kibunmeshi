package com.kibunmeshi.controller;

import com.kibunmeshi.domain.Food;
import com.kibunmeshi.repository.FoodMapper;
import com.kibunmeshi.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DataInitController {

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private javax.sql.DataSource dataSource;

    @GetMapping("/reload-sample-data")
    public String reloadSampleData() {
        try (java.sql.Connection conn = dataSource.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            stmt.execute("DELETE FROM recommendation_histories;");
            stmt.execute("DELETE FROM foods;");
            stmt.execute("DELETE FROM food_categories;");
            stmt.execute("DELETE FROM emotions;");
            stmt.execute("DELETE FROM food_advice_templates;");
            // 주의: users 테이블은 사용자 데이터 보존을 위해 제외함
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

            org.springframework.core.io.Resource resource = 
                new org.springframework.core.io.ClassPathResource("data.sql");
            org.springframework.jdbc.datasource.init.ResourceDatabasePopulator populator = 
                new org.springframework.jdbc.datasource.init.ResourceDatabasePopulator(resource);
            populator.execute(dataSource);
            return "✅ DB에 36가지 전 조합 샘플 데이터(총 74개 음식) 재로드 완료! (사용자 데이터 보존됨)";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ DB 재로드 중 오류 발생: " + e.getMessage();
        }
    }

    @GetMapping("/init-data")
    public String initData() {
        // emotion=4 (つかれた) + category=1 (韓国料理) 데이터 추가
        Food food1 = new Food();
        food1.setName("冷麺");
        food1.setEmotionId(4);
        food1.setCategoryId(1);
        food1.setDescription("冷たくて辛い麺。夏の疲れを癒す韓国の冷麺。");
        food1.setEffect("冷たい麺が体を引き締め、疲労回復を助けます。");
        food1.setAdviceType("spicy");
        food1.setImageKeyword("naengmyeon");
        food1.setRarity("common");
        food1.setIsActive(true);
        foodMapper.insert(food1);

        Food food2 = new Food();
        food2.setName("キムチチゲ");
        food2.setEmotionId(4);
        food2.setCategoryId(1);
        food2.setDescription("発酵食品の力で体を元気に。キムチと豚肉を煮込んだ韓国の代表的な鍋料理。");
        food2.setEffect("発酵食品の乳酸菌が腸内環境を整え、免疫力を高めます。");
        food2.setAdviceType("healing");
        food2.setImageKeyword("kimchi stew");
        food2.setRarity("common");
        food2.setIsActive(true);
        foodMapper.insert(food2);

        Food food3 = new Food();
        food3.setName("サムゲタン");
        food3.setEmotionId(4);
        food3.setCategoryId(1);
        food3.setDescription("滋養強壮の代表料理。鶏の腹に高麗人参、もち米などを詰めて煮込んだスープ。");
        food3.setEffect("高麗人参のサポニン成分が疲労回復を助け、体力を回復させます。");
        food3.setAdviceType("energy");
        food3.setImageKeyword("samgyetang");
        food3.setRarity("rare");
        food3.setIsActive(true);
        foodMapper.insert(food3);

        return "데이터 추가 완료: 冷麺, キムチチゲ, サムゲタン";
    }

    @GetMapping("/test-gemini")
    public Map<String, Object> testGemini() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            System.out.println("=== Gemini API 테스트 시작 ===");
            String response = geminiService.generateFoodRecommendation(
                "tired", "つかれた", 
                "korean", "韓国料理"
            );
            
            result.put("success", true);
            result.put("response", response);
            result.put("message", "Gemini API 호출 성공");
            System.out.println("=== Gemini API 테스트 성공 ===");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("message", "Gemini API 호출 실패");
            System.err.println("=== Gemini API 테스트 실패 ===");
            e.printStackTrace();
        }
        
        return result;
    }
}
