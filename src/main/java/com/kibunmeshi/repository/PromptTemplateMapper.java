package com.kibunmeshi.repository;

import com.kibunmeshi.domain.PromptTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PromptTemplateMapper {
    PromptTemplate findByEmotionAndGenre(@Param("emotion") String emotion, @Param("genre") String genre);
}
