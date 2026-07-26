package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * アドバイステンプレート（AdviceTemplate）エンティティ
 */
@Data
public class AdviceTemplate {
    
    private Integer id;
    private Integer emotionId;
    private String adviceType;
    private String templateJa;
    private String templateKo;
    private String geminiPrompt;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
