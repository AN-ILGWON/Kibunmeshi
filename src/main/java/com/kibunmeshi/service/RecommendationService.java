package com.kibunmeshi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibunmeshi.domain.*;
import com.kibunmeshi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class RecommendationService {
    
    @Autowired
    private FoodMapper foodMapper;
    
    @Autowired
    private EmotionMapper emotionMapper;
    
    @Autowired
    private FoodCategoryMapper categoryMapper;
    
    @Autowired
    private AdviceTemplateMapper adviceTemplateMapper;
    
    @Autowired
    private HistoryMapper historyMapper;
    
    @Autowired
    private GeminiService geminiService;
    
    @Autowired
    private ImageSearchService imageSearchService;
    
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Recommendation recommend(Integer emotionId, Integer categoryId, Integer excludeFoodId) {
        System.out.println("=== RecommendationService.recommend 시작 ===");
        System.out.println("emotionId: " + emotionId + ", categoryId: " + categoryId);
        
        Emotion emotion = emotionMapper.findById(emotionId);
        FoodCategory category = categoryMapper.findById(categoryId);
        
        System.out.println("emotion: " + (emotion != null ? emotion.getName() : "null"));
        System.out.println("category: " + (category != null ? category.getName() : "null"));
        
        // 1. Gemini AI 추천 시도
        if (geminiService.isEnabled()) {
            System.out.println("Gemini AI 추천 시도 중...");
            try {
                Food aiFood = getAIRecommendation(emotion, category);
                if (aiFood != null && aiFood.getName() != null && !aiFood.getName().trim().isEmpty()) {
                    String advice = aiFood.getEffect() != null && !aiFood.getEffect().trim().isEmpty() 
                                    ? aiFood.getEffect() : aiFood.getDescription();
                    System.out.println("Gemini AI 추천 성공: " + aiFood.getName());
                    return new Recommendation(aiFood, emotion, category, advice);
                }
            } catch (Exception e) {
                System.err.println("Gemini AI 추천 중 예외 발생 -> DB 폴백 진행: " + e.getMessage());
            }
            System.out.println("Gemini AI 추천 결과 없음 -> DB 폴백 처리");
        }
        
        // 2. DB에서 감정 및 카테고리에 맞는 음식 목록 조회 (랜덤 1개 선택)
        List<Food> foods = foodMapper.findByEmotionAndCategory(emotionId, categoryId);
        System.out.println("DB에서 찾은 foods 개수: " + foods.size());
        
        if (foods.isEmpty()) {
            System.out.println("DB에 foods가 없어서 기본 음식 생성");
            Food defaultFood = createDefaultFood(emotion, category);
            String advice = defaultFood.getEffect();
            return new Recommendation(defaultFood, emotion, category, advice);
        }
        
        // 제외할 음식이 있으면 필터링 (다시 뽑기 시)
        if (excludeFoodId != null && foods.size() > 1) {
            foods.removeIf(f -> f.getId().equals(excludeFoodId));
        }
        
        Food food = foods.get(random.nextInt(foods.size()));
        
        // DB Food의 imageUrl 설정
        if (food.getImageUrl() == null || food.getImageUrl().trim().isEmpty()) {
            String keyword = food.getImageKeyword() != null && !food.getImageKeyword().trim().isEmpty() 
                             ? food.getImageKeyword() : food.getName();
            food.setImageUrl(imageSearchService.searchImage(keyword));
        }
        
        System.out.println("선택된 DB food: " + food.getName());
        String advice = generateAdvice(food, emotion);
        
        return new Recommendation(food, emotion, category, advice);
    }
    
    private Food getAIRecommendation(Emotion emotion, FoodCategory category) {
        System.out.println("=== getAIRecommendation 시작 ===");
        System.out.println("emotion: " + emotion.getName() + " (" + emotion.getLabel() + ")");
        System.out.println("category: " + category.getName() + " (" + category.getLabel() + ")");
        
        String aiResponse = geminiService.generateFoodRecommendation(
            emotion.getName(), emotion.getLabel(),
            category.getName(), category.getLabel()
        );
        
        System.out.println("=== AI 응답 ===");
        System.out.println("aiResponse: " + aiResponse);
        
        if (aiResponse == null || aiResponse.isEmpty()) {
            System.out.println("=== AI 응답이 비어있음 ===");
            return null;
        }
        
        try {
            JsonNode root = objectMapper.readTree(aiResponse);
            
            System.out.println("=== JSON 파싱 결과 ===");
            System.out.println("root: " + root);
            
            String name = root.path("dishName").asText();
            String description = root.path("description").asText();
            String effect = root.path("effect").asText();
            String imageKeyword = root.path("imagePrompt").asText();
            
            System.out.println("=== 추출된 필드 ===");
            System.out.println("dishName: " + name);
            System.out.println("description: " + description);
            System.out.println("effect: " + effect);
            System.out.println("imagePrompt: " + imageKeyword);
            
            if (effect == null || effect.isEmpty()) {
                effect = description;
                System.out.println("effect가 비어있어서 description 사용");
            }
            
            // AI가 반환한 음식으로 Food 객체 생성
            Food food = new Food();
            food.setName(name);
            food.setDescription(description);
            food.setEffect(effect);
            
            // AI가 제공한 이미지 키워드로 이미지 검색
            String searchKeyword = imageKeyword != null && !imageKeyword.isEmpty() ? imageKeyword : name;
            System.out.println("이미지 검색 키워드: " + searchKeyword);
            String imageUrl = imageSearchService.searchImage(searchKeyword);
            System.out.println("이미지 URL: " + imageUrl);
            
            food.setImageUrl(imageUrl);
            food.setImageKeyword(imageKeyword);
            
            food.setEmotionId(emotion.getId());
            food.setCategoryId(category.getId());
            food.setAdviceType("general");
            food.setIsActive(true);
            food.setRarity(rollRarity());

            // AI가 새로 생성한 음식을 DB foods 테이블에 자동 저장
            try {
                foodMapper.insert(food);
                System.out.println("=== AI 추천 음식 DB 자동 저장 완료 (Food ID: " + food.getId() + ") ===");
            } catch (Exception ex) {
                System.err.println("AI 추천 음식 DB 저장 실패 (진행은 계속됨): " + ex.getMessage());
            }
            
            System.out.println("=== AI Food 객체 생성 완료 ===");
            System.out.println("food.name: " + food.getName());
            System.out.println("food.description: " + food.getDescription());
            System.out.println("food.effect: " + food.getEffect());
            System.out.println("food.imageUrl: " + food.getImageUrl());
            
            return food;
        } catch (Exception e) {
            System.err.println("=== AI 응답 파싱 중 오류 발생 ===");
            e.printStackTrace();
            return null;
        }
    }
    
    public Food createDefaultFood(Emotion emotion, FoodCategory category) {
        System.out.println("=== 기본 음식 생성 ===");
        Food food = new Food();
        
        // 감정과 카테고리에 따른 기본 음식 생성
        String defaultName = category.getLabel() + "の特別料理";
        String defaultDescription = emotion.getLabel() + "気分にぴったりの" + category.getLabel() + "です。";
        String defaultEffect = emotion.getLabel() + "時の心と体をケアします。";
        
        food.setName(defaultName);
        food.setDescription(defaultDescription);
        food.setEffect(defaultEffect);
        food.setEmotionId(emotion.getId());
        food.setCategoryId(category.getId());
        food.setAdviceType("general");
        food.setIsActive(true);
        food.setRarity("common");
        
        // 기본 이미지 검색
        String searchKeyword = category.getLabel() + " food";
        String imageUrl = imageSearchService.searchImage(searchKeyword);
        food.setImageUrl(imageUrl);
        food.setImageKeyword(searchKeyword);
        
        System.out.println("기본 음식 생성 완료: " + food.getName());
        return food;
    }
    
    private String generateAdvice(Food food, Emotion emotion) {
        AdviceTemplate template = adviceTemplateMapper.findByEmotionAndType(
            emotion.getId(), food.getAdviceType()
        );
        
        if (template != null && template.getTemplateJa() != null) {
            return template.getTemplateJa().replace("{food_name}", food.getName())
                                           .replace("【料理名】", food.getName());
        }
        
        return food.getName() + "を楽しんでください。";
    }

    private String rollRarity() {
        int roll = random.nextInt(100);
        if (roll < 1) {
            return "ur";
        }
        if (roll < 6) {
            return "sr";
        }
        if (roll < 26) {
            return "rare";
        }
        return "common";
    }
    
    public Integer saveHistory(Integer userId, Recommendation recommendation, String adviceText) {
        Food food = recommendation.getFood();
        if (food.getId() == null) {
            if (food.getRarity() == null || food.getRarity().isEmpty()) {
                food.setRarity(rollRarity());
            }
            if (food.getEffect() == null || food.getEffect().isEmpty()) {
                food.setEffect(food.getDescription());
            }
            foodMapper.insert(food);
        }

        RecommendationHistory history = new RecommendationHistory();
        history.setUserId(userId);
        history.setFoodId(recommendation.getFood().getId());
        history.setEmotionId(recommendation.getEmotion().getId());
        history.setCategoryId(recommendation.getCategory().getId());
        history.setIsFavorite(false);
        history.setIsPublic(false);
        history.setAdviceText(adviceText);
        history.setCreatedAt(java.time.LocalDateTime.now());
        
        historyMapper.insert(history);
        return history.getId();
    }
    
    public static class Recommendation {
        private final Food food;
        private final Emotion emotion;
        private final FoodCategory category;
        private final String advice;
        
        public Recommendation(Food food, Emotion emotion, FoodCategory category, String advice) {
            this.food = food;
            this.emotion = emotion;
            this.category = category;
            this.advice = advice;
        }
        
        public Food getFood() { return food; }
        public Emotion getEmotion() { return emotion; }
        public FoodCategory getCategory() { return category; }
        public String getAdvice() { return advice; }
    }
}
