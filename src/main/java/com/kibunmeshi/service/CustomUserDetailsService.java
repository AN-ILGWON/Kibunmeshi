package com.kibunmeshi.service;

import com.kibunmeshi.domain.User;
import com.kibunmeshi.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        // #region debug-point B:load-user
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
            new org.springframework.web.client.RestTemplate().postForObject(
                    url,
                    Map.of(
                            "sessionId", sessionId,
                            "runId", "pre-fix",
                            "hypothesisId", "B",
                            "location", "CustomUserDetailsService.loadUserByUsername",
                            "msg", "[DEBUG] loaded login user",
                            "data", Map.of(
                                    "username", username,
                                    "userFound", user != null,
                                    "enabled", user != null && Boolean.TRUE.equals(user.getEnabled()),
                                    "role", user != null && user.getRole() != null ? user.getRole() : "null"
                            ),
                            "ts", System.currentTimeMillis()
                    ),
                    String.class
            );
        } catch (Exception ignored) {
        }
        // #endregion
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
