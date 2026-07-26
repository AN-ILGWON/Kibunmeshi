package com.kibunmeshi.service;

import com.kibunmeshi.domain.FoodCategory;
import com.kibunmeshi.repository.FoodCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodCategoryService {
    
    @Autowired
    private FoodCategoryMapper categoryMapper;
    
    public List<FoodCategory> getAllCategories() {
        return categoryMapper.findAll();
    }
    
    public FoodCategory getCategoryById(Integer id) {
        return categoryMapper.findById(id);
    }
}
