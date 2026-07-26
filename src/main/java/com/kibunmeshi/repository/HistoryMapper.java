package com.kibunmeshi.repository;

import com.kibunmeshi.domain.RecommendationHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface HistoryMapper {
    void insert(RecommendationHistory history);
    List<RecommendationHistory> findByUserId(Integer userId);
    List<RecommendationHistory> findByUserIdAndFavorite(Integer userId, Boolean isFavorite);
    List<RecommendationHistory> findPublicCards();
    void updateFavorite(@Param("id") Integer id, @Param("isFavorite") Boolean isFavorite);
    void updatePublic(@Param("id") Integer id, @Param("isPublic") Boolean isPublic);
    void delete(Integer id);
    RecommendationHistory findById(Integer id);
}
