package com.kibunmeshi.controller;

import com.kibunmeshi.domain.Emotion;
import com.kibunmeshi.domain.FoodCategory;
import com.kibunmeshi.service.EmotionService;
import com.kibunmeshi.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {
    
    @Autowired
    private EmotionService emotionService;
    
    @Autowired
    private FoodCategoryService categoryService;
    
    @GetMapping("/category")
    public String category(@RequestParam("emotion") Integer emotionId, Model model) {
        Emotion emotion = emotionService.getEmotionById(emotionId);
        model.addAttribute("emotion", emotion);
        
        List<FoodCategory> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        
        return "category";
    }
}
