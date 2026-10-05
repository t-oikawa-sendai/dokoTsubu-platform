/*
 * Program Name: CsrfTokenInterceptor
 * Language: Java
 * Function: Issue a session CSRF token and reject state-changing POST requests when the token is missing or does not match
 * Created: 2026-10-05
 * Last Updated: 2026-10-05
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. Session-stored token for POST /Main, POST /UpdateMutter, and POST /DeleteMutter. No Spring Security. HttpSession and Spring Session JDBC stay unchanged.
 */

package dokotsubu.config;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CsrfTokenInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    static final String TOKEN_NAME = "csrfToken";

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this)
                .addPathPatterns("/Main", "/SearchMutter", "/UpdateMutter", "/DeleteMutter")
                .order(1);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return true;
        }
        boolean stateChangingPost = handler instanceof HandlerMethod handlerMethod
                && handlerMethod.hasMethodAnnotation(PostMapping.class);
        String token;
        synchronized (WebUtils.getSessionMutex(session)) {
            token = (String) session.getAttribute(TOKEN_NAME);
            if (stateChangingPost && !matches(token, request.getParameter(TOKEN_NAME))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            if (token == null) {
                token = newToken();
                session.setAttribute(TOKEN_NAME, token);
            }
        }
        request.setAttribute(TOKEN_NAME, token);
        return true;
    }

    private String newToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static boolean matches(String expected, String actual) {
        if (expected == null || actual == null) {
            return false;
        }
        return MessageDigest.isEqual(
                expected.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                actual.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
