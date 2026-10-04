/*
 * Program Name: SessionCookieBeforeViewInterceptor
 * Language: Java
 * Function: Write the Spring Session cookie before the view commits the response
 * Created: 2026-10-04
 * Last Updated: 2026-10-04
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. JSP commits the response before SessionRepositoryFilter can add Set-Cookie, and Tomcat drops that late header. HttpSession and loginUser stay unchanged.
 */

package dokotsubu.config;

import java.util.List;

import org.springframework.session.web.http.CookieSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionCookieBeforeViewInterceptor implements HandlerInterceptor, WebMvcConfigurer {

    private final CookieSerializer cookieSerializer;

    public SessionCookieBeforeViewInterceptor(CookieSerializer cookieSerializer) {
        this.cookieSerializer = cookieSerializer;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        if (response.isCommitted()) {
            return;
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        List<String> existingIds = cookieSerializer.readCookieValues(request);
        if (existingIds.contains(session.getId())) {
            return;
        }
        cookieSerializer.writeCookieValue(new CookieSerializer.CookieValue(request, response, session.getId()));
    }
}
