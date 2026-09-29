/*
 * Program Name: MainController
 * Language: Java
 * Function: Show the mutter list and accept a mutter post for /Main
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004, FR-005, and FR-009. Login check stays in the interceptor.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.model.LoginUser;
import dokotsubu.service.MutterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    private final MutterService mutterService;

    public MainController(MutterService mutterService) {
        this.mutterService = mutterService;
    }

    @GetMapping("/Main")
    public String showMain(HttpServletRequest request) {
        request.setAttribute("mutterList", mutterService.findAll());
        return "main";
    }

    @PostMapping("/Main")
    public String postMain(
            @RequestParam(value = "text", required = false) String text,
            HttpServletRequest request) {
        if (text == null || text.length() == 0) {
            request.setAttribute("errorMsg", "つぶやきが入力されていません");
        } else {
            HttpSession session = request.getSession(false);
            LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
            String aiMsg = mutterService.post(loginUser.getId(), text);
            if (aiMsg != null) {
                request.setAttribute("aiMsg", aiMsg);
            }
        }
        request.setAttribute("mutterList", mutterService.findAll());
        return "main";
    }
}
