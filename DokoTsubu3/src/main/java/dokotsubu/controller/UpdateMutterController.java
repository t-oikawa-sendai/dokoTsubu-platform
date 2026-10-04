/*
 * Program Name: UpdateMutterController
 * Language: Java
 * Function: Show and update the login user's own mutter for /UpdateMutter
 * Created: 2026-09-29
 * Last Updated: 2026-10-04
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-007. Login check stays in the interceptor. Not owned or missing mutter redirects to /Main. Update failure keeps the current failure message and returns to the edit screen.
 */

package dokotsubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dokotsubu.model.LoginUser;
import dokotsubu.model.Mutter;
import dokotsubu.service.MutterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateMutterController {

    private final MutterService mutterService;

    public UpdateMutterController(MutterService mutterService) {
        this.mutterService = mutterService;
    }

    @GetMapping("/UpdateMutter")
    public String showUpdateMutter(
            @RequestParam("id") int id,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        Mutter mutter = mutterService.findOwned(id, loginUser.getId());
        if (mutter == null) {
            return "redirect:/Main";
        }
        request.setAttribute("mutter", mutter);
        return "updateMutter";
    }

    @PostMapping("/UpdateMutter")
    public String postUpdateMutter(
            @RequestParam("id") int id,
            @RequestParam(value = "text", required = false) String text,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        Mutter owned = mutterService.findOwned(id, loginUser.getId());
        if (owned == null) {
            return "redirect:/Main";
        }
        if (text == null || text.length() == 0
                || !mutterService.updateOwned(id, loginUser.getId(), text)) {
            request.setAttribute("errorMsg", "更新できませんでした。ID・本文・DB を確認してください。");
        } else {
            return "redirect:/Main";
        }
        request.setAttribute("mutter",
                new Mutter(owned.getId(), owned.getUserId(), owned.getUserName(), text));
        return "updateMutter";
    }
}
