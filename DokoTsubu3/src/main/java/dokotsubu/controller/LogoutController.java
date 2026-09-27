/*
 * Program Name: LogoutController
 * Language: Java
 * Function: Invalidate the current HttpSession and show the logout screen
 * Created: 2026-09-27
 * Last Updated: 2026-09-27
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-003. GET /Logout only. No Service or DAO.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/Logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "logout";
    }
}
