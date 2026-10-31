package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.DeleteMutterService;

@Controller
public class DeleteMutterController {

    @GetMapping("/DeleteMutter")
    public String delete(
    		@RequestParam("id") String id) {

        // DeleteMutterServiceインスタンスの生成
        DeleteMutterService delMutterService = new DeleteMutterService();

        // 削除処理
        delMutterService.execute(id);

        // Mainへリダイレクト
        return "redirect:/Main";
    }
}