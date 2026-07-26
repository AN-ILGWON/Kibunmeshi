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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MypageController {
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/mypage")
    public String mypage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        
        List<RecommendationHistory> cards = historyService.getUserHistories(user.getId());
        List<RecommendationHistory> favorites = historyService.getFavoriteHistories(user.getId());
        
        model.addAttribute("user", user);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCount", cards.size());
        model.addAttribute("favCount", favorites.size());
        model.addAttribute("viewMode", "all");
        
        return "mypage";
    }
    
    @GetMapping("/mypage/favorites")
    public String mypageFavorites(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        
        List<RecommendationHistory> cards = historyService.getFavoriteHistories(user.getId());
        List<RecommendationHistory> allCards = historyService.getUserHistories(user.getId());
        
        model.addAttribute("user", user);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCount", allCards.size());
        model.addAttribute("favCount", cards.size());
        model.addAttribute("viewMode", "favorites");
        
        return "mypage";
    }
    
    @PostMapping("/mypage/favorite")
    public String toggleFavorite(
            @RequestParam("historyId") Integer historyId,
            @RequestParam(value = "redirect", required = false) String redirect,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        
        // Check if user owns this card or has collected it
        User user = userService.findByUsername(userDetails.getUsername());
        RecommendationHistory history = historyService.findById(historyId);
        
        if (history == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "カードが見つかりません");
            return "redirect:/mypage";
        }
        
        // Allow favorite toggle only if user owns the card
        if (!history.getUserId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "このカードをお気に入りに変更する権限がありません");
            return "redirect:/mypage";
        }
        
        historyService.toggleFavorite(historyId);
        
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/mypage";
    }
    
    @PostMapping("/mypage/public")
    public String togglePublic(
            @RequestParam("historyId") Integer historyId,
            @RequestParam("isPublic") Boolean isPublic,
            @RequestParam(value = "redirect", required = false) String redirect,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        
        // Check if user owns this card
        User user = userService.findByUsername(userDetails.getUsername());
        RecommendationHistory history = historyService.findById(historyId);
        
        if (history == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "カードが見つかりません");
            return "redirect:/mypage";
        }
        
        // Only original poster can toggle public/private
        if (!history.getUserId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "このカードを公開/非公開に変更する権限がありません");
            return "redirect:/mypage";
        }
        
        historyService.togglePublic(historyId);
        
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/mypage";
    }
    
    @PostMapping("/mypage/delete")
    public String deleteHistory(
            @RequestParam("historyId") Integer historyId,
            @RequestParam(value = "redirect", required = false) String redirect,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        
        // Check if user owns this card
        User user = userService.findByUsername(userDetails.getUsername());
        RecommendationHistory history = historyService.findById(historyId);
        
        if (history == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "カードが見つかりません");
            return "redirect:/mypage";
        }
        
        // Only original poster can delete
        if (!history.getUserId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "このカードを削除する権限がありません");
            return "redirect:/mypage";
        }
        
        historyService.deleteHistory(historyId);
        
        if (redirect != null && !redirect.isEmpty()) {
            return "redirect:" + redirect;
        }
        return "redirect:/mypage";
    }
    
    @PostMapping("/mypage/collect")
    public String collectCard(
            @RequestParam("historyId") Integer historyId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        
        if (userDetails == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByUsername(userDetails.getUsername());
        if (user != null) {
            historyService.collectCard(historyId, user.getId());
            redirectAttributes.addFlashAttribute("signupSuccess", "カードをコレクションに収集しました！");
        }
        
        return "redirect:/mypage";
    }
}
