package com.kibunmeshi.controller;

import com.kibunmeshi.domain.User;
import com.kibunmeshi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "signupSuccess", required = false) String signupSuccess,
            Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "ログインIDまたはパスワードが正しくありません。");
        }
        
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", "会員登録が完了しました。ログインしてください。");
        }
        
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/api/check-username")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam("username") String username) {
        Map<String, Object> response = new HashMap<>();
        boolean available = userService.isUsernameAvailable(username);
        response.put("available", available);
        response.put("message", available ? "使用可能なログインIDです。" : "このログインIDは既に使用されています。");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/check-nickname")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestParam("nickname") String nickname) {
        Map<String, Object> response = new HashMap<>();
        boolean available = userService.isNicknameAvailable(nickname);
        response.put("available", available);
        response.put("message", available ? "使用可能なニックネームです。" : "このニックネームは既に使用されています。");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    public String signup(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("passwordConfirm") String passwordConfirm,
            @RequestParam("nickname") String nickname,
            @RequestParam("email") String email,
            RedirectAttributes redirectAttributes) {
        
        // 비밀번호 확인
        if (!password.equals(passwordConfirm)) {
            redirectAttributes.addFlashAttribute("signupError", "パスワードが一致しません。");
            return "redirect:/signup";
        }
        
        // 중복 체크
        if (!userService.isUsernameAvailable(username)) {
            redirectAttributes.addFlashAttribute("signupError", "このログインIDは既に使用されています。");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("nickname", nickname);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/signup";
        }
        
        if (!userService.isNicknameAvailable(nickname)) {
            redirectAttributes.addFlashAttribute("signupError", "このニックネームは既に使用されています。");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("nickname", nickname);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/signup";
        }
        
        if (!userService.isEmailAvailable(email)) {
            redirectAttributes.addFlashAttribute("signupError", "このメールアドレスは既に使用されています。");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("nickname", nickname);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/signup";
        }
        
        // 사용자 등록
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(password);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setRole("USER");
        user.setEnabled(true);
        
        try {
            userService.registerUser(user);
            return "redirect:/login?signupSuccess=true";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("signupError", "会員登録に失敗しました。");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("nickname", nickname);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/signup";
        }
    }
}
