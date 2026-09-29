/*
 * Program Name: Mutter
 * Language: Java
 * Function: Hold one mutter row for the list screen
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004. id, userName, and text only.
 */

package dokotsubu.model;

public class Mutter {

    private final int id;
    private final String userName;
    private final String text;

    public Mutter(int id, String userName, String text) {
        this.id = id;
        this.userName = userName;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }
}
