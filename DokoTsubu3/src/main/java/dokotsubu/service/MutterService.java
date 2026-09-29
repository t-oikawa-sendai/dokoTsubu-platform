/*
 * Program Name: MutterService
 * Language: Java
 * Function: Return the mutter list, search mutters by keyword, save a mutter before asking Gemini, and find, update, or delete an owned mutter
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor
 * Memo: Phase 1 DokoTsubu3 FR-004, FR-005, FR-006, FR-007, FR-008, and FR-009. Gemini runs only after INSERT succeeds. No rollback. Edit and delete pass mutter id and login user id to DAO.
 */

package dokotsubu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dokotsubu.client.GeminiClient;
import dokotsubu.dao.MutterDAO;
import dokotsubu.model.Mutter;

@Service
public class MutterService {

    private final MutterDAO mutterDAO;
    private final GeminiClient geminiClient;

    public MutterService(MutterDAO mutterDAO, GeminiClient geminiClient) {
        this.mutterDAO = mutterDAO;
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
        return geminiClient.generateShortComment(text);
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
