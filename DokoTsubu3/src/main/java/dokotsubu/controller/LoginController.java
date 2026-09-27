/*
 * Program Name: LoginController
 * Language: Java
 * Function: Show login entry and handle POST /Login authentication
 * Created: 2026-09-13
 * Last Updated: 2026-09-27
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. This POST result is request attribute loginSuccess. Failure does not clear session.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.model.LoginUser;
import dokotsubu.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/Login")
    public String showLogin() {
        return "index";
    }

    @PostMapping("/Login")
    public String login(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pass", required = false) String pass,
            HttpServletRequest request) {
        if ((name == null || name.length() == 0)
                || (pass == null || pass.length() == 0)) {
            request.setAttribute("loginSuccess", Boolean.FALSE);
            request.setAttribute("errorMsg", "必要項目が未入力です。");
            return "loginResult";
        }

        LoginUser loginUser = loginService.login(name, pass);
        if (loginUser != null) {
            request.getSession().setAttribute("loginUser", loginUser);
            request.setAttribute("loginSuccess", Boolean.TRUE);
        } else {
            request.setAttribute("loginSuccess", Boolean.FALSE);
            request.setAttribute("errorMsg", "パスワードが間違っているか、ユーザーが未登録です。");
        }
        return "loginResult";
    }
}
