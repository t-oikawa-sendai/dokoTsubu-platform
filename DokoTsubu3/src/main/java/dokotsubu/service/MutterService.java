/*
 * Program Name: MutterService
 * Language: Java
 * Function: Return the mutter list and save a mutter before asking Gemini
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004, FR-005, and FR-009. Gemini runs only after INSERT succeeds. No rollback.
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

    public String post(int userId, String text) {
        if (!mutterDAO.create(userId, text)) {
            return null;
        }
        return geminiClient.generateShortComment(text);
    }
}
