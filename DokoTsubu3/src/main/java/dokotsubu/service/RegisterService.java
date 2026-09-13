/*
 * Program Name: RegisterService
 * Language: Java
 * Function: Hash a password with BCrypt and request user registration
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. DAO receives username and hash only.
 */

package dokotsubu.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dokotsubu.dao.UserDAO;
import dokotsubu.model.RegisterResult;

@Service
public class RegisterService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public RegisterResult register(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return userDAO.registerUser(username, hashedPassword);
    }
}
