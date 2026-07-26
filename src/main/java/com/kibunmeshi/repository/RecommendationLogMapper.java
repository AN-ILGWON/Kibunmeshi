package com.kibunmeshi.repository;

import com.kibunmeshi.domain.RecommendationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendationLogMapper {
    void insert(RecommendationLog log);
}
