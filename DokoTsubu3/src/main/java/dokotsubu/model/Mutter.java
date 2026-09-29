/*
 * Program Name: Mutter
 * Language: Java
 * Function: Hold one mutter row for the list, search, and edit screens
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor
 * Memo: Phase 1 DokoTsubu3 FR-004 and FR-007. id, userId, userName, and text. userId is used for owner checks.
 */

package dokotsubu.model;

public class Mutter {

    private final int id;
    private final int userId;
    private final String userName;
    private final String text;

    public Mutter(int id, int userId, String userName, String text) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }
}
