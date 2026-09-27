/*
 * Program Name: LoginService
 * Language: Java
 * Function: Authenticate name and pass against a stored BCrypt hash
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. Returns LoginUser only. No hash to Controller.
 */

package dokotsubu.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dokotsubu.dao.UserDAO;
import dokotsubu.model.LoginUser;
import dokotsubu.model.UserCredential;

@Service
public class LoginService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public LoginUser login(String name, String pass) {
        UserCredential credential = userDAO.findByName(name);
        if (credential == null) {
            return null;
        }
        if (!passwordEncoder.matches(pass, credential.getPasswordHash())) {
            return null;
        }
        return new LoginUser(credential.getId(), credential.getName());
    }
}
