package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UpdateMutterService;

@Controller
public class UpdateMutterController {
    @GetMapping("/UpdateMutter")
    public String showUpdate(
            @RequestParam("id") String id,
            Model model) {

        // リクエストスコープへ保存
        model.addAttribute("id", id);

        // updateMutter.jspへ
        return "updateMutter";
    }

    @PostMapping("/UpdateMutter")
    public String update(
            @RequestParam("id") String id,
            @RequestParam("text") String text) {

        // UpdateMutterServiceインスタンスの生成
        UpdateMutterService updMutter = new UpdateMutterService();

        // 更新処理
        updMutter.execute(id, text);

        // Mainへリダイレクト
        return "redirect:/Main";
    }
}
