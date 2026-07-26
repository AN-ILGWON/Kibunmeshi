package com.kibunmeshi.dto;

import lombok.Data;

/**
 * 추천 요청 DTO
 */
@Data
public class RecommendationRequest {
    private String emotion;
    private String genre;
}
