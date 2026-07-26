package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecommendationLog {
    private Integer id;
    private String emotion;
    private String genre;
    private String dishName;
    private String description;
    private String imagePrompt;
    private LocalDateTime createdAt;
}
