package com.kibunmeshi.controller;

import com.kibunmeshi.domain.Emotion;
import com.kibunmeshi.domain.Food;
import com.kibunmeshi.domain.FoodCategory;
import com.kibunmeshi.domain.RecommendationHistory;
import com.kibunmeshi.domain.User;
import com.kibunmeshi.service.EmotionService;
import com.kibunmeshi.service.FoodCategoryService;
import com.kibunmeshi.service.HistoryService;
import com.kibunmeshi.service.RecommendationService;
import com.kibunmeshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ResultController {
    
    @Autowired
    private RecommendationService recommendationService;
    
    @Autowired
    private EmotionService emotionService;
    
    @Autowired
    private FoodCategoryService categoryService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private UserService userService;
    
    // AI 호출 진행 상황 저장 (세션 ID -> 진행 상황)
    private final Map<String, LoadingStatus> loadingStatusMap = new ConcurrentHashMap<>();
    
    private static class LoadingStatus {
        int progress = 0;
        boolean completed = false;
        String errorMessage = null;
        
        public int getProgress() { return progress; }
        public boolean isCompleted() { return completed; }
        public String getErrorMessage() { return errorMessage; }
        
        public void setProgress(int progress) { this.progress = progress; }
        public void setCompleted(boolean completed) { this.completed = completed; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    }
    
    @GetMapping("/loading")
    public String loading(
            @RequestParam("emotion") Integer emotionId,
            @RequestParam("category") Integer categoryId,
            @RequestParam(value = "exclude", required = false) Integer excludeFoodId,
            Model model) {
        
        // 감정과 카테고리 정보 가져오기
        Emotion emotion = emotionService.getEmotionById(emotionId);
        FoodCategory category = categoryService.getCategoryById(categoryId);
        
        model.addAttribute("emotion", emotion);
        model.addAttribute("category", category);
        
        return "loading";
    }
    
    @GetMapping("/api/loading-status")
    @ResponseBody
    public Map<String, Object> getLoadingStatus(@RequestParam("sessionId") String sessionId) {
        LoadingStatus status = loadingStatusMap.get(sessionId);
        Map<String, Object> response = new java.util.HashMap<>();
        
        if (status == null) {
            response.put("progress", 0);
            response.put("completed", false);
            response.put("error", "Session not found");
        } else {
            response.put("progress", status.getProgress());
            response.put("completed", status.isCompleted());
            response.put("error", status.getErrorMessage());
        }
        
        return response;
    }
    
    @GetMapping("/result")
    public String result(
            @RequestParam("emotion") Integer emotionId,
            @RequestParam("category") Integer categoryId,
            @RequestParam(value = "exclude", required = false) Integer excludeFoodId,
            @RequestParam(value = "reroll", required = false) Integer reroll,
            @RequestParam(value = "sessionId", required = false) String sessionId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        try {
            System.out.println("=== /result 요청 시작 ===");
            System.out.println("emotionId: " + emotionId);
            System.out.println("categoryId: " + categoryId);
            
            // 감정과 카테고리 정보 가져오기
            Emotion emotion = emotionService.getEmotionById(emotionId);
            FoodCategory category = categoryService.getCategoryById(categoryId);
            
            // DB에 데이터가 있는지 확인
            System.out.println("DB 조회 시작...");
            
            RecommendationService.Recommendation recommendation = 
                recommendationService.recommend(emotionId, categoryId, excludeFoodId);
            
            System.out.println("recommendation: " + (recommendation != null ? "성공" : "null"));
            
            if (recommendation == null) {
                System.out.println("recommendation이 null이어서 기본 음식 생성");
                // recommendation이 null이면 기본 음식 생성
                Food defaultFood = recommendationService.createDefaultFood(emotion, category);
                String defaultAdvice = defaultFood.getEffect();
                model.addAttribute("food", defaultFood);
                model.addAttribute("emotion", emotion);
                model.addAttribute("category", category);
                model.addAttribute("advice", defaultAdvice);
                model.addAttribute("imageUrl", defaultFood.getUnsplashImageUrl());
                model.addAttribute("isLoggedIn", false);
                return "result";
            }
            
            model.addAttribute("food", recommendation.getFood());
            model.addAttribute("emotion", recommendation.getEmotion());
            model.addAttribute("category", recommendation.getCategory());
            model.addAttribute("advice", recommendation.getAdvice());
            
            String imageUrl = recommendation.getFood().getUnsplashImageUrl();
            model.addAttribute("imageUrl", imageUrl);
            
            System.out.println("=== Model Attributes ===");
            System.out.println("food.name: " + recommendation.getFood().getName());
            System.out.println("food.description: " + recommendation.getFood().getDescription());
            System.out.println("food.effect: " + recommendation.getFood().getEffect());
            System.out.println("imageUrl: " + imageUrl);
            System.out.println("emotion.label: " + recommendation.getEmotion().getLabel());
            System.out.println("category.label: " + recommendation.getCategory().getLabel());
            System.out.println("advice: " + recommendation.getAdvice());
            
            if (userDetails != null) {
                User user = userService.findByUsername(userDetails.getUsername());
                Integer historyId = recommendationService.saveHistory(
                    user.getId(), recommendation, recommendation.getAdvice()
                );
                model.addAttribute("savedHistoryId", historyId);
                model.addAttribute("savedToCard", true);
                model.addAttribute("isLoggedIn", true);
            } else {
                model.addAttribute("isLoggedIn", false);
            }
            
            System.out.println("=== /result 요청 성공, result 반환 ===");
            return "result";
        } catch (Exception e) {
            System.err.println("=== /result 요청 중 오류 발생 ===");
            e.printStackTrace();
            model.addAttribute("error", "오류가 발생했습니다: " + e.getMessage());
            return "result-empty";
        }
    }
}
