package com.kibunmeshi.service;

import com.kibunmeshi.domain.User;
import com.kibunmeshi.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    public void registerUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userMapper.insert(user);
    }
    
    public boolean isUsernameAvailable(String username) {
        return userMapper.countByUsername(username) == 0;
    }
    
    public boolean isNicknameAvailable(String nickname) {
        return userMapper.countByNickname(nickname) == 0;
    }
    
    public boolean isEmailAvailable(String email) {
        return userMapper.countByEmail(email) == 0;
    }
    
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
