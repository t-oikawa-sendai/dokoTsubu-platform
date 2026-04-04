package model;

import dao.MuttersDAO;

public class UpdateMutterLogic {

	/**
	 * @return UPDATE が 1 行成功したとき true（ID 不正・0 件・DB 失敗は false）
	 */
	public boolean execute(String strId, String text) {
		// Update:20260404 空 ID は更新しない（parseInt 例外で握りつぶされていた問題の排除）
		if (strId == null || strId.isBlank()) {
			return false;
		}
		int id;
		try {
			id = Integer.parseInt(strId.trim());
		} catch (NumberFormatException e) {
			return false;
		}
		MuttersDAO dao = new MuttersDAO();
		return dao.update(id, text);
	}
}
