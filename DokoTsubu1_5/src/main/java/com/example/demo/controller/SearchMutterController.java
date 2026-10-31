package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Mutter;
import com.example.demo.model.User;
import com.example.demo.service.SearchMutterService;

@Controller
public class SearchMutterController {

    @GetMapping("/SearchMutter")
    public String search(
            @RequestParam("keyword") String keyword,
            Model model,
            HttpSession session) {

        // つぶやきを検索
        SearchMutterService searchMutterService = new SearchMutterService();
        List<Mutter> mutterList = searchMutterService.execute(keyword);

        // リクエストスコープへ保存
        model.addAttribute("mutterList", mutterList);

        // セッションスコープからログイン情報取得
        User loginUser = (User) session.getAttribute("loginUser");

        // ログイン確認
        if (loginUser == null) {

            // リダイレクト
            return "redirect:/";
        }

        // main.jspへフォワード
        return "main";
    }
}
