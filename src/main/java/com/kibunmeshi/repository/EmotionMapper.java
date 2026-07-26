package com.kibunmeshi.repository;

import com.kibunmeshi.domain.Emotion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EmotionMapper {
    List<Emotion> findAll();
    Emotion findById(Integer id);
}
