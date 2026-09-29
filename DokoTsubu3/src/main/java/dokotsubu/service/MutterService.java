/*
 * Program Name: MutterService
 * Language: Java
 * Function: Return the mutter list from MutterDAO to the controller
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004. List retrieval only.
 */

package dokotsubu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dokotsubu.dao.MutterDAO;
import dokotsubu.model.Mutter;

@Service
public class MutterService {

    private final MutterDAO mutterDAO;

    public MutterService(MutterDAO mutterDAO) {
        this.mutterDAO = mutterDAO;
    }

    public List<Mutter> findAll() {
        return mutterDAO.findAll();
    }
}
