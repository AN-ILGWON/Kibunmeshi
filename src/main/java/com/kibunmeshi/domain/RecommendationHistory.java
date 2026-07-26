package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 推薦履歴（RecommendationHistory）エンティティ
 */
@Data
public class RecommendationHistory {
    
    private Integer id;
    private Integer userId;
    private Integer foodId;
    private Integer emotionId;
    private Integer categoryId;
    private Boolean isFavorite;
    private Boolean isPublic;
    private String adviceText;
    private LocalDateTime createdAt;
    
    // JOIN用の関連エンティティ
    private Food food;
    private Emotion emotion;
    private FoodCategory category;
}
