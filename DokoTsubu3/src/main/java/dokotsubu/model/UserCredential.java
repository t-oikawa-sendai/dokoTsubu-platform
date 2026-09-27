/*
 * Program Name: UserCredential
 * Language: Java
 * Function: Carry one USERS row from DAO to LoginService
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. DAO to Service only. Not stored in session.
 */

package dokotsubu.model;

public class UserCredential {

    private final int id;
    private final String name;
    private final String passwordHash;

    public UserCredential(int id, String name, String passwordHash) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
