package com.kibunmeshi.config;

import com.kibunmeshi.domain.Food;
import com.kibunmeshi.repository.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component // 일시적으로 비활성화
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public void run(String... args) throws Exception {
        // emotion=4 (つかれた) + category=1 (韓国料理) 데이터가 있는지 확인
        try {
            List<Food> existingFoods = foodMapper.findByEmotionAndCategory(4, 1);
            if (existingFoods.isEmpty()) {
                System.out.println("emotion=4 + category=1 데이터가 없어서 추가합니다.");
                addMissingData();
            } else {
                System.out.println("emotion=4 + category=1 데이터가 이미 존재합니다: " + existingFoods.size() + "개");
            }
        } catch (Exception e) {
            System.err.println("데이터 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addMissingData() {
        try {
            // emotion=4 + category=1 데이터 추가
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
            System.out.println("데이터 추가: 冷麺");
        } catch (Exception e) {
            System.out.println("冷麺 데이터 추가 실패 (이미 존재할 수 있음): " + e.getMessage());
        }

        try {
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
            System.out.println("데이터 추가: キムチチゲ");
        } catch (Exception e) {
            System.out.println("キムチチゲ 데이터 추가 실패 (이미 존재할 수 있음): " + e.getMessage());
        }

        try {
            Food food3 = new Food();
            food3.setName("サムゲタン");
            food3.setEmotionId(4);
            food3.setCategoryId(1);
            food3.setDescription("滋養強壮の代表料理。鶏の腹に高麗人参、もち米などを詰めて煮込んだスープ。");
            food3.setEffect("高麗人参のサポニン成分が疲労回復を助け、体力を回復させます。");
            food3.setAdviceType("power");
            food3.setImageKeyword("samgyetang");
            food3.setRarity("rare");
            food3.setIsActive(true);
            foodMapper.insert(food3);
            System.out.println("데이터 추가: サムゲタン");
        } catch (Exception e) {
            System.out.println("サムゲタン 데이터 추가 실패 (이미 존재할 수 있음): " + e.getMessage());
        }
    }
}
