package com.kibunmeshi.controller;

import com.kibunmeshi.domain.Emotion;
import com.kibunmeshi.domain.RecommendationHistory;
import com.kibunmeshi.service.EmotionService;
import com.kibunmeshi.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TopController {
    
    @Autowired
    private EmotionService emotionService;
    
    @Autowired
    private HistoryService historyService;
    
    @GetMapping("/")
    public String top(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Emotion> emotions = emotionService.getAllEmotions();
        model.addAttribute("emotions", emotions);
        
        if (userDetails != null) {
            // #region debug-point A:top-entry
            try {
                String url = "http://127.0.0.1:7777/event";
                String sessionId = "login-500-error";
                try {
                    String env = Files.readString(Path.of(".dbg", "login-500-error.env"));
                    url = Arrays.stream(env.split("\\R"))
                            .filter(line -> line.startsWith("DEBUG_SERVER_URL="))
                            .map(line -> line.substring("DEBUG_SERVER_URL=".length()))
                            .findFirst()
                            .orElse(url);
                    sessionId = Arrays.stream(env.split("\\R"))
                            .filter(line -> line.startsWith("DEBUG_SESSION_ID="))
                            .map(line -> line.substring("DEBUG_SESSION_ID=".length()))
                            .findFirst()
                            .orElse(sessionId);
                } catch (Exception ignored) {
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                        Map.of(
                                "sessionId", sessionId,
                                "runId", "pre-fix",
                                "hypothesisId", "A",
                                "location", "TopController.top:authenticated",
                                "msg", "[DEBUG] entering authenticated top",
                                "data", Map.of("username", userDetails.getUsername()),
                                "ts", System.currentTimeMillis()
                        ),
                        headers
                );
                new org.springframework.web.client.RestTemplate().postForObject(url, entity, String.class);
            } catch (Exception ignored) {
            }
            // #endregion
            try {
                List<RecommendationHistory> publicCards = historyService.getPublicCards();
                model.addAttribute("publicCards", publicCards);
                // #region debug-point D:public-cards-loaded
                try {
                    String url = "http://127.0.0.1:7777/event";
                    String sessionId = "login-500-error";
                    try {
                        String env = Files.readString(Path.of(".dbg", "login-500-error.env"));
                        url = Arrays.stream(env.split("\\R"))
                                .filter(line -> line.startsWith("DEBUG_SERVER_URL="))
                                .map(line -> line.substring("DEBUG_SERVER_URL=".length()))
                                .findFirst()
                                .orElse(url);
                        sessionId = Arrays.stream(env.split("\\R"))
                                .filter(line -> line.startsWith("DEBUG_SESSION_ID="))
                                .map(line -> line.substring("DEBUG_SESSION_ID=".length()))
                                .findFirst()
                                .orElse(sessionId);
                    } catch (Exception ignored) {
                    }
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                            Map.of(
                                    "sessionId", sessionId,
                                    "runId", "pre-fix",
                                    "hypothesisId", "D",
                                    "location", "TopController.top:publicCards",
                                    "msg", "[DEBUG] loaded public cards",
                                    "data", Map.of("count", publicCards.size()),
                                    "ts", System.currentTimeMillis()
                            ),
                            headers
                    );
                    new org.springframework.web.client.RestTemplate().postForObject(url, entity, String.class);
                } catch (Exception ignored) {
                }
                // #endregion
            } catch (RuntimeException e) {
                // #region debug-point D:public-cards-error
                try {
                    String url = "http://127.0.0.1:7777/event";
                    String sessionId = "login-500-error";
                    try {
                        String env = Files.readString(Path.of(".dbg", "login-500-error.env"));
                        url = Arrays.stream(env.split("\\R"))
                                .filter(line -> line.startsWith("DEBUG_SERVER_URL="))
                                .map(line -> line.substring("DEBUG_SERVER_URL=".length()))
                                .findFirst()
                                .orElse(url);
                        sessionId = Arrays.stream(env.split("\\R"))
                                .filter(line -> line.startsWith("DEBUG_SESSION_ID="))
                                .map(line -> line.substring("DEBUG_SESSION_ID=".length()))
                                .findFirst()
                                .orElse(sessionId);
                    } catch (Exception ignored) {
                    }
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                            Map.of(
                                    "sessionId", sessionId,
                                    "runId", "pre-fix",
                                    "hypothesisId", "D",
                                    "location", "TopController.top:publicCards",
                                    "msg", "[DEBUG] public cards loading failed",
                                    "data", Map.of(
                                            "exceptionClass", e.getClass().getName(),
                                            "message", e.getMessage() != null ? e.getMessage() : "null"
                                    ),
                                    "ts", System.currentTimeMillis()
                            ),
                            headers
                    );
                    new org.springframework.web.client.RestTemplate().postForObject(url, entity, String.class);
                } catch (Exception ignored) {
                }
                // #endregion
                throw e;
            }
        }
        
        return "top";
    }
}
