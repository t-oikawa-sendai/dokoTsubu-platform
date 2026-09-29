/*
 * Program Name: SearchMutterController
 * Language: Java
 * Function: Show mutters matching keyword for /SearchMutter
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-006. Login check stays in the interceptor.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.service.MutterService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SearchMutterController {

    private final MutterService mutterService;

    public SearchMutterController(MutterService mutterService) {
        this.mutterService = mutterService;
    }

    @GetMapping("/SearchMutter")
    public String searchMutter(
            @RequestParam("keyword") String keyword,
            HttpServletRequest request) {
        request.setAttribute("mutterList", mutterService.search(keyword));
        return "main";
    }
}
