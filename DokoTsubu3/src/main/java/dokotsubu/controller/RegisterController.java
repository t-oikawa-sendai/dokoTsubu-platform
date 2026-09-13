/*
 * Program Name: RegisterController
 * Language: Java
 * Function: Return the user registration JSP for GET /Register
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3 GET /Register only. No registration processing.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/Register")
    public String showRegister() {
        return "registerView";
    }
}
