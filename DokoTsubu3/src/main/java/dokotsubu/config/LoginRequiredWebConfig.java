/*
 * Program Name: LoginRequiredWebConfig
 * Language: Java
 * Function: Register LoginRequiredInterceptor for paths that require login
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor
 * Memo: Phase 1 DokoTsubu3 FR-004, FR-006, FR-007, and FR-008. /Main, /SearchMutter, /UpdateMutter, and /DeleteMutter.
 */

package dokotsubu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginRequiredWebConfig implements WebMvcConfigurer {

    private final LoginRequiredInterceptor loginRequiredInterceptor;

    public LoginRequiredWebConfig(LoginRequiredInterceptor loginRequiredInterceptor) {
        this.loginRequiredInterceptor = loginRequiredInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor)
                .addPathPatterns("/Main", "/SearchMutter", "/UpdateMutter", "/DeleteMutter");
    }
}
