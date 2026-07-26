package com.kibunmeshi.service;

import com.kibunmeshi.domain.Emotion;
import com.kibunmeshi.repository.EmotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmotionService {
    
    @Autowired
    private EmotionMapper emotionMapper;
    
    public List<Emotion> getAllEmotions() {
        return emotionMapper.findAll();
    }
    
    public Emotion getEmotionById(Integer id) {
        return emotionMapper.findById(id);
    }
}
