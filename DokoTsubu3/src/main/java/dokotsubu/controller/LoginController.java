/*
 * Program Name: LoginController
 * Language: Java
 * Function: Return the login entry JSP for GET /Login
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3 GET /Login only. No authentication.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/Login")
    public String showLogin() {
        return "index";
    }
}
