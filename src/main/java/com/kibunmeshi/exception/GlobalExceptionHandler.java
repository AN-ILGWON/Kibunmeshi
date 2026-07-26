package com.kibunmeshi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(GeminiApiException.class)
    public ResponseEntity<Map<String, Object>> handleGeminiApiException(GeminiApiException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "GEMINI_API_ERROR");
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "INVALID_ARGUMENT");
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e, HttpServletRequest request) {
        // 실제 Exception Stack Trace 출력
        e.printStackTrace();
        
        // #region debug-point C:generic-exception
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
                            "hypothesisId", "C",
                            "location", "GlobalExceptionHandler.handleGenericException",
                            "msg", "[DEBUG] generic exception handled",
                            "data", Map.of(
                                    "uri", request != null ? request.getRequestURI() : "unknown",
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
        Map<String, Object> response = new HashMap<>();
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "サーバーエラーが発生しました。");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
