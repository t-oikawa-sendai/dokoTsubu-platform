/*
 * Program Name: RegisterController
 * Language: Java
 * Function: Show the registration form and handle POST /Register
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. GET is view only. POST validates then calls Service.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.model.RegisterResult;
import dokotsubu.service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/Register")
    public String showRegister() {
        return "registerView";
    }

    @PostMapping("/Register")
    public String register(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            HttpServletRequest request) {
        if ((username == null || username.length() == 0)
                || (password == null || password.length() == 0)) {
            request.setAttribute("errorMsg", "必要項目が未入力です。");
            return "registerView";
        }

        RegisterResult result = registerService.register(username, password);
        if (result == RegisterResult.SUCCESS) {
            return "registerResult";
        }
        if (result == RegisterResult.DUPLICATE) {
            request.setAttribute("errorMsg", "ユーザー名が既に存在します。");
            return "registerView";
        }
        request.setAttribute("errorMsg", "登録できませんでした。最初からやり直してください。");
        return "registerView";
    }
}
