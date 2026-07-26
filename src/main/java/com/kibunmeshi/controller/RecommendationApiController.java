package com.kibunmeshi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibunmeshi.domain.PromptTemplate;
import com.kibunmeshi.domain.RecommendationLog;
import com.kibunmeshi.dto.RecommendationRequest;
import com.kibunmeshi.dto.RecommendationResponse;
import com.kibunmeshi.exception.GeminiApiException;
import com.kibunmeshi.repository.PromptTemplateMapper;
import com.kibunmeshi.repository.RecommendationLogMapper;
import com.kibunmeshi.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecommendationApiController {
    
    @Autowired
    private GeminiService geminiService;
    
    @Autowired
    private PromptTemplateMapper promptTemplateMapper;
    
    @Autowired
    private RecommendationLogMapper recommendationLogMapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostMapping("/recommendations")
    public ResponseEntity<RecommendationResponse> getRecommendation(@RequestBody RecommendationRequest request) {
        if (!geminiService.isEnabled()) {
            throw new GeminiApiException("Gemini API가 활성화되지 않았습니다.");
        }
        
        String emotion = request.getEmotion();
        String genre = request.getGenre();
        
        if (emotion == null || emotion.isEmpty() || genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("감정과 장르를 모두 입력해주세요.");
        }
        
        try {
            // 프롬프트 템플릿 로드
            PromptTemplate template = promptTemplateMapper.findByEmotionAndGenre(emotion, genre);
            String prompt = template != null ? template.getTemplate() : buildDefaultPrompt(emotion, genre);
            
            // Gemini API 호출
            String aiResponse = geminiService.generateFoodRecommendation(emotion, emotion, genre, genre);
            
            if (aiResponse == null || aiResponse.isEmpty()) {
                throw new GeminiApiException("AI 추천을 가져오지 못했습니다.");
            }
            
            JsonNode root = objectMapper.readTree(aiResponse);
            
            RecommendationResponse response = new RecommendationResponse();
            response.setDishName(root.path("dishName").asText());
            response.setDescription(root.path("description").asText());
            response.setImagePrompt(root.path("imagePrompt").asText());
            
            // 로그 저장
            RecommendationLog log = new RecommendationLog();
            log.setEmotion(emotion);
            log.setGenre(genre);
            log.setDishName(response.getDishName());
            log.setDescription(response.getDescription());
            log.setImagePrompt(response.getImagePrompt());
            recommendationLogMapper.insert(log);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new GeminiApiException("AI 추천 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    private String buildDefaultPrompt(String emotion, String genre) {
        return String.format(
            "당신은 영양학과 심리학에 정통한 AI 어시스턴트입니다.\n" +
            "사용자의 현재 감정: %s\n" +
            "원하는 요리 장르: %s\n\n" +
            "이 감정과 상태에 도움이 되는 %s 요리 1가지를 추천해주세요.\n\n" +
            "다음 JSON 형식으로만 응답하세요 (다른 텍스트 없이):\n" +
            "{\n" +
            "  \"dishName\": \"추천 요리 이름 (일본어)\",\n" +
            "  \"description\": \"카드용 감성 문구 (일본어, 2-3문장)\",\n" +
            "  \"imagePrompt\": \"요리 이미지 생성을 위한 영어 프롬프트\"\n" +
            "}",
            emotion, genre, genre
        );
    }
}
