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
import java.util.Random;

@Service
public class ImageSearchService {
    
    @Value("${unsplash.access-key:}")
    private String accessKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    
    private static final Map<String, String> FALLBACK_IMAGES = new HashMap<>();
    
    static {
        FALLBACK_IMAGES.put("samgyetang", "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("dakgalbi", "https://images.unsplash.com/photo-1567620832903-9fc6debc209f?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("bibimbap", "https://images.unsplash.com/photo-1553163147-622ab57be1c7?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("budae", "https://images.unsplash.com/photo-1543339308-43e59d6b73a6?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("kimchi", "https://images.unsplash.com/photo-1583032015879-c5db14032d8d?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("naengmyeon", "https://images.unsplash.com/photo-1617093727343-374698b1b08d?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("onigiri", "https://images.unsplash.com/photo-1618841557871-b4664f4f2323?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("udon", "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("ochazuke", "https://images.unsplash.com/photo-1512058564366-18510be2db19?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("oden", "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("mapo", "https://images.unsplash.com/photo-1541696432-82c6da8ce7bf?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("fried rice", "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("xiaolongbao", "https://images.unsplash.com/photo-1496116218417-1a781b1c416c?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("tantanmen", "https://images.unsplash.com/photo-1552611052-33e04de081de?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("hamburg", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("omurice", "https://images.unsplash.com/photo-1525351484163-7529414344d8?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("pasta", "https://images.unsplash.com/photo-1621996346565-e3def6164286?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("curry", "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("biryani", "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop");
        FALLBACK_IMAGES.put("salad", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600&auto=format&fit=crop");
    }

    public ImageSearchService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000); // 3초 연결 타임아웃
        factory.setReadTimeout(5000); // 5초 읽기 타임아웃
        this.restTemplate = new RestTemplate(factory);
    }
    
    public String searchImage(String keyword) {
        if (keyword != null && (keyword.startsWith("http://") || keyword.startsWith("https://"))) {
            return keyword;
        }

        if (accessKey != null && !accessKey.trim().isEmpty()) {
            try {
                String url = "https://api.unsplash.com/search/photos?query=" + 
                             keyword.replace(" ", "+") + 
                             "&per_page=10&orientation=landscape";
                
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Client-ID " + accessKey.trim());
                
                HttpEntity<Void> entity = new HttpEntity<>(headers);
                
                ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class
                );
                
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    JsonNode root = objectMapper.readTree(response.getBody());
                    JsonNode results = root.path("results");
                    
                    if (results.isArray() && results.size() > 0) {
                        int index = random.nextInt(results.size());
                        JsonNode image = results.get(index);
                        String regularUrl = image.path("urls").path("regular").asText();
                        if (regularUrl != null && !regularUrl.isEmpty()) {
                            return regularUrl;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Unsplash API image search failed for keyword [" + keyword + "]: " + e.getMessage());
            }
        }
        
        return getFallbackImage(keyword);
    }
    
    public String getFallbackImage(String keyword) {
        if (keyword != null) {
            String lower = keyword.toLowerCase();
            for (Map.Entry<String, String> entry : FALLBACK_IMAGES.entrySet()) {
                if (lower.contains(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=600&auto=format&fit=crop";
    }
    
    public boolean isApiKeySet() {
        return accessKey != null && !accessKey.trim().isEmpty();
    }
}
