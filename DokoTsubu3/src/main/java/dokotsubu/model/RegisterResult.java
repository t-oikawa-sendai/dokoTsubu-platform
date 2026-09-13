/*
 * Program Name: RegisterResult
 * Language: Java
 * Function: Registration outcome returned from Service to Controller
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. SUCCESS / DUPLICATE / DB_ERROR only.
 */

package dokotsubu.model;

public enum RegisterResult {
    SUCCESS,
    DUPLICATE,
    DB_ERROR
}
