package com.kibunmeshi.controller;

import com.kibunmeshi.domain.RecommendationHistory;
import com.kibunmeshi.domain.User;
import com.kibunmeshi.service.HistoryService;
import com.kibunmeshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CardDetailController {
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/card-detail")
    public String cardDetail(
            @RequestParam("historyId") Integer historyId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        RecommendationHistory card = historyService.findById(historyId);
        
        if (card == null) {
            return "redirect:/mypage";
        }
        
        // 公開カードは誰でも閲覧可能、非公開カードは所有者のみ閲覧可能
        boolean isOwner = false;
        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user != null && card.getUserId() != null && card.getUserId().equals(user.getId())) {
                isOwner = true;
            }
        }
        
        if (!card.getIsPublic() && !isOwner) {
            return "redirect:/mypage";
        }
        
        model.addAttribute("card", card);
        model.addAttribute("isOwner", isOwner);
        
        return "card-detail";
    }
}
