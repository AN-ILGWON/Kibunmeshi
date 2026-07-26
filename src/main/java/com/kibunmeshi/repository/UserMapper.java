package com.kibunmeshi.repository;

import com.kibunmeshi.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void insert(User user);
    User findById(Integer id);
    List<User> findAll();
    int countByUsername(String username);
    int countByNickname(String nickname);
    int countByEmail(String email);
}
