/*
 * Program Name: UserProfile
 * Language: Java
 * Function: Carry GENDER and AGE_FEELING, and share allowed codes with display names
 * Created: 2026-10-08
 * Last Updated: 2026-10-08
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. Registration validation and Gemini display names use the same codes. Not stored in Session.
 */

package dokotsubu.model;

import java.util.Map;

public final class UserProfile {

    private static final Map<String, String> GENDER_DISPLAY_NAMES = Map.of(
            "MALE", "男性",
            "FEMALE", "女性",
            "OTHER", "その他",
            "PRIVATE", "ヒミツ");

    private static final Map<String, String> AGE_FEELING_DISPLAY_NAMES = Map.of(
            "YOUNG", "若い",
            "FAIRLY_YOUNG", "そこそこ若い",
            "MIDDLE", "中年",
            "PRE_SENIOR", "高齢者の少し手前",
            "SENIOR", "がっつり高齢者");

    private final String gender;
    private final String ageFeeling;

    public UserProfile(String gender, String ageFeeling) {
        this.gender = gender;
        this.ageFeeling = ageFeeling;
    }

    public static boolean isAllowedGender(String value) {
        return value != null && GENDER_DISPLAY_NAMES.containsKey(value);
    }

    public static boolean isAllowedAgeFeeling(String value) {
        return value != null && AGE_FEELING_DISPLAY_NAMES.containsKey(value);
    }

    public String getGender() {
        return gender;
    }

    public String getAgeFeeling() {
        return ageFeeling;
    }

    public String genderDisplayName() {
        return GENDER_DISPLAY_NAMES.get(gender);
    }

    public String ageFeelingDisplayName() {
        return AGE_FEELING_DISPLAY_NAMES.get(ageFeeling);
    }

    public boolean isPrivateGender() {
        return "PRIVATE".equals(gender);
    }
}
