package com.kibunmeshi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiService {
    
    @Value("${gemini.api-key:}")
    private String apiKey;
    
    @Value("${gemini.text-model:gemini-3.6-flash}")
    private String textModel;
    
    @Value("${gemini.image-model:gemini-3.1-flash-image}")
    private String imageModel;
    
    @Value("${gemini.enabled:false}")
    private boolean enabled;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public GeminiService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10초 연결 타임아웃
        factory.setReadTimeout(30000); // 30초 읽기 타임아웃
        this.restTemplate = new RestTemplate(factory);
    }
    
    public String generateFoodRecommendation(String emotion, String emotionLabel, String category, String categoryLabel) {
        if (!enabled || apiKey == null || apiKey.isEmpty()) {
            return null;
        }
        
        String prompt = buildPrompt(emotion, emotionLabel, category, categoryLabel);
        
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + textModel + ":generateContent?key=" + apiKey;
            
            System.out.println("=== Gemini API 호출 ===");
            System.out.println("URL: " + url);
            System.out.println("Model: " + textModel);
            System.out.println("API Key: " + (apiKey != null && !apiKey.isEmpty() ? "설정됨" : "비어있음"));
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            });
            
            // System Instruction과 response_mime_type 설정
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("response_mime_type", "application/json");
            requestBody.put("generationConfig", generationConfig);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            
            System.out.println("=== Gemini API 응답 ===");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body (원문): " + response.getBody());
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode text = root.path("candidates").get(0).path("content").path("parts").get(0).path("text");
                String result = text.asText();
                System.out.println("=== Gemini API 추출된 text ===");
                System.out.println(result);
                return result;
            } else {
                System.out.println("=== Gemini API 응답 실패 ===");
                System.out.println("Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("=== Gemini API 호출 중 오류 발생 ===");
            e.printStackTrace();
        }
        
        System.out.println("=== Gemini API null 반환 ===");
        return null;
    }
    
    private String buildPrompt(String emotion, String emotionLabel, String category, String categoryLabel) {
        return String.format(
            "당신은 영양학과 심리학에 정통한 AI 어시스턴트입니다.\n" +
            "사용자의 현재 감정: %s (%s)\n" +
            "원하는 요리 장르: %s (%s)\n\n" +
            "이 감정과 상태에 도움이 되는 %s 요리 1가지를 추천해주세요.\n\n" +
            "다음 JSON 형식으로만 응답하세요 (다른 텍스트 없이):\n" +
            "{\n" +
            "  \"dishName\": \"추천 요리 이름 (일본어)\",\n" +
            "  \"description\": \"카드용 감성 문구 (일본어, 2-3문장)\",\n" +
            "  \"effect\": \"栄養成分・効果の説明 (일본어, 1-2문장)\",\n" +
            "  \"imagePrompt\": \"요리 이미지 생성을 위한 영어 프롬프트\"\n" +
            "}\n\n" +
            "예시:\n" +
            "{\n" +
            "  \"dishName\": \"サムゲタン\",\n" +
            "  \"description\": \"鶏の腹に高麗人参、もち米などを詰めて煮込んだ滋養強壮スープ。体を温める効果があります。\",\n" +
            "  \"effect\": \"高麗人参のサポニン成分が疲労回復を助け、免疫力を向上させます。\",\n" +
            "  \"imagePrompt\": \"Korean samgyetang soup with ginseng and glutinous rice in clay pot, warm and nourishing\"\n" +
            "}",
            emotionLabel, emotion, categoryLabel, category, categoryLabel
        );
    }
    
    public boolean isEnabled() {
        return enabled && apiKey != null && !apiKey.isEmpty();
    }
}
