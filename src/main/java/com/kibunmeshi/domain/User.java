package com.kibunmeshi.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * ユーザー（User）エンティティ
 */
@Data
public class User {
    
    private Integer id;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String role;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
