package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.RegisterUserService;

@Controller
public class RegisterController {

	private final RegisterUserService registerUserService;
	public RegisterController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}
		
	// 登録フォームを表示する
	@GetMapping("/register")
	public String showRegisterForm() {
			return "register";
	}
	
	// 登録処理
	@PostMapping("/register")
	public String register(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model) {
		
		// 入力チェック
		if ((username != null && !username.isEmpty())
				&& (password != null && !password.isEmpty())) {
			
			System.out.println("正常処理に入る");
			
			// ユーザー作成
			User user = new User(username,password);
			
			// 登録処理
			boolean result = registerUserService.execute(user);
			
			// 登録成功
			if (result) {
				return "registerResult";
			} else {
				model.addAttribute("errorMsg", "登録できませんでした。最初からやり直してください");
				return "register";
			}
		} else {
			// 未入力エラー
			model.addAttribute("errorMsg", "必要項目が未入力です。");
			return "register";
		}
	}
}
