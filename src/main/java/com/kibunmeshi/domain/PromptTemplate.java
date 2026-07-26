package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PromptTemplate {
    private Integer id;
    private String emotion;
    private String genre;
    private String template;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
