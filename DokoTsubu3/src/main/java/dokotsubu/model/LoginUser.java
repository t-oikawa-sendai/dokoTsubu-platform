/*
 * Program Name: LoginUser
 * Language: Java
 * Function: Hold authenticated user id and name in session
 * Created: 2026-09-13
 * Last Updated: 2026-10-04
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. Session key loginUser. Serializable for Spring Session JDBC. No password fields.
 */

package dokotsubu.model;

import java.io.Serializable;

public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
