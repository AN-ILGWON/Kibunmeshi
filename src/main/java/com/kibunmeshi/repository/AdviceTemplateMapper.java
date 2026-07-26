package com.kibunmeshi.repository;

import com.kibunmeshi.domain.AdviceTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AdviceTemplateMapper {
    AdviceTemplate findByEmotionAndType(@Param("emotionId") Integer emotionId, @Param("adviceType") String adviceType);
    List<AdviceTemplate> findAll();
}
