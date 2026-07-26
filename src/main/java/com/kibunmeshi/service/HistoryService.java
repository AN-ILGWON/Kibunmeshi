package com.kibunmeshi.service;

import com.kibunmeshi.domain.RecommendationHistory;
import com.kibunmeshi.repository.HistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryService {
    
    @Autowired
    private HistoryMapper historyMapper;
    
    public List<RecommendationHistory> getUserHistories(Integer userId) {
        return historyMapper.findByUserId(userId);
    }
    
    public List<RecommendationHistory> getFavoriteHistories(Integer userId) {
        return historyMapper.findByUserIdAndFavorite(userId, true);
    }
    
    public List<RecommendationHistory> getPublicCards() {
        try {
            List<RecommendationHistory> result = historyMapper.findPublicCards();
            if (result == null) {
                return List.of();
            }
            // null인 요소 필터링
            return result.stream()
                    .filter(card -> card != null)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public void toggleFavorite(Integer historyId) {
        RecommendationHistory history = historyMapper.findById(historyId);
        if (history != null) {
            historyMapper.updateFavorite(historyId, !history.getIsFavorite());
        }
    }
    
    public void togglePublic(Integer historyId) {
        RecommendationHistory history = historyMapper.findById(historyId);
        if (history != null) {
            historyMapper.updatePublic(historyId, !history.getIsPublic());
        }
    }
    
    public void deleteHistory(Integer historyId) {
        historyMapper.delete(historyId);
    }
    
    public RecommendationHistory findById(Integer id) {
        return historyMapper.findById(id);
    }
    
    public RecommendationHistory collectCard(Integer sourceHistoryId, Integer currentUserId) {
        RecommendationHistory source = historyMapper.findById(sourceHistoryId);
        if (source == null || currentUserId == null) {
            return null;
        }
        
        RecommendationHistory newCard = new RecommendationHistory();
        newCard.setUserId(currentUserId);
        newCard.setFoodId(source.getFoodId());
        newCard.setEmotionId(source.getEmotionId());
        newCard.setCategoryId(source.getCategoryId());
        newCard.setAdviceText(source.getAdviceText());
        newCard.setIsFavorite(true); // 수집된 카드는 자동으로 즐겨찾기에 등록
        newCard.setIsPublic(false); // 수집한 카드는 기본적으로 본인 컬렉션에 보관
        
        historyMapper.insert(newCard);
        return newCard;
    }
}
