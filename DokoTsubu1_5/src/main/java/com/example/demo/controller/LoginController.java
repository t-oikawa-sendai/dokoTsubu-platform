package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.LoginService;

@Controller
public class LoginController {
	private final LoginService loginService;
	
	// コンストラクタインジェクション
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/Login")
	public String login(
			@RequestParam("name") String name,
			@RequestParam("pass") String pass,
			HttpSession session,
			Model model) {
		
		// ユーザー名・パスワードがどちらも入力さている場合
		if((name != null && !name.isEmpty())
				&&(pass != null && !pass.isEmpty())) {
			
			// 入力値でUserインスタンス生成
			User user = new User(name,pass);
			
			// DB存在チェック
			User findUser = loginService.find(user);
			
			// ログイン処理
			if (findUser != null) {
				
				// セッションスコープに保存
				session.setAttribute("loginUser", findUser);
			} else {
				// エラーメッセージ
				model.addAttribute("errorMsg", "パスワードが間違っているか、ユーザーが未登録です。");
			}
		} else {
			// 未入力エラー
			model.addAttribute("errorMsg", "必要項目が未入力です。");
		}
		
		// ログイン結果画面へ
		return "loginResult";
	}
}