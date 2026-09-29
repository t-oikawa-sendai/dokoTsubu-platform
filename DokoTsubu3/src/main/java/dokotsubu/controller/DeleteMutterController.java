/*
 * Program Name: DeleteMutterController
 * Language: Java
 * Function: Delete the login user's own mutter for GET /DeleteMutter
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor
 * Memo: Phase 1 DokoTsubu3 FR-008. Login check stays in the interceptor. Always redirects to /Main.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.model.LoginUser;
import dokotsubu.service.MutterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteMutterController {

    private final MutterService mutterService;

    public DeleteMutterController(MutterService mutterService) {
        this.mutterService = mutterService;
    }

    @GetMapping("/DeleteMutter")
    public String deleteMutter(
            @RequestParam("id") int id,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        mutterService.deleteOwned(id, loginUser.getId());
        return "redirect:/Main";
    }
}
