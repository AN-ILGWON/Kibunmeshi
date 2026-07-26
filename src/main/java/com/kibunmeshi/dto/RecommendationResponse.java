package com.kibunmeshi.dto;

import lombok.Data;

/**
 * 추천 응답 DTO
 */
@Data
public class RecommendationResponse {
    private String dishName;
    private String description;
    private String imagePrompt;
}
