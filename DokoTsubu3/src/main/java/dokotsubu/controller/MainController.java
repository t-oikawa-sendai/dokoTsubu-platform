/*
 * Program Name: MainController
 * Language: Java
 * Function: Show the mutter list for GET /Main
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004. Login check is not in this controller.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import dokotsubu.service.MutterService;
import jakarta.servlet.http.HttpServletRequest;

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
}
