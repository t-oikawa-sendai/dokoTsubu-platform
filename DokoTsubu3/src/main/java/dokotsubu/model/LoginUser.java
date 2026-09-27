/*
 * Program Name: LoginUser
 * Language: Java
 * Function: Hold authenticated user id and name in session
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. Session key loginUser. No password fields.
 */

package dokotsubu.model;

public class LoginUser {

    private final int id;
    private final String name;

    public LoginUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
