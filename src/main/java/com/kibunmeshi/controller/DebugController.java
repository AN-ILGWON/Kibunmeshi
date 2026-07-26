package com.kibunmeshi.controller;

import com.kibunmeshi.domain.User;
import com.kibunmeshi.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DebugController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/debug/users")
    public List<User> getAllUsers() {
        // 간단히 모든 유저를 반환 (개발용으로만 사용!)
        return userMapper.findAll();
    }
}
