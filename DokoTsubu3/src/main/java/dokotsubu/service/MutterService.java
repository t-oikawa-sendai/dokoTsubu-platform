/*
 * Program Name: MutterService
 * Language: Java
 * Function: Return the mutter list, search mutters by keyword, save a mutter before asking Gemini, and find, update, or delete an owned mutter
 * Created: 2026-09-29
 * Last Updated: 2026-10-08
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. Gemini runs only after INSERT succeeds, using the active user's DB profile. No rollback.
 */

package dokotsubu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dokotsubu.client.GeminiClient;
import dokotsubu.dao.MutterDAO;
import dokotsubu.dao.UserDAO;
import dokotsubu.model.Mutter;
import dokotsubu.model.UserProfile;

@Service
public class MutterService {

    private final MutterDAO mutterDAO;
    private final UserDAO userDAO;
    private final GeminiClient geminiClient;

    public MutterService(MutterDAO mutterDAO, UserDAO userDAO, GeminiClient geminiClient) {
        this.mutterDAO = mutterDAO;
        this.userDAO = userDAO;
        this.geminiClient = geminiClient;
    }

    public List<Mutter> findAll() {
        return mutterDAO.findAll();
    }

    public List<Mutter> search(String keyword) {
        return mutterDAO.search(keyword);
    }

    public String post(int userId, String text) {
        if (!mutterDAO.create(userId, text)) {
            return null;
        }
        UserProfile profile = userDAO.findActiveProfileByUserId(userId);
        if (profile == null) {
            return GeminiClient.FAILURE_MESSAGE;
        }
        return geminiClient.generateShortComment(text, profile);
    }

    public Mutter findOwned(int id, int userId) {
        return mutterDAO.findByIdAndUserId(id, userId);
    }

    public boolean updateOwned(int id, int userId, String text) {
        return mutterDAO.update(id, userId, text);
    }

    public boolean deleteOwned(int id, int userId) {
        return mutterDAO.delete(id, userId);
    }
}
