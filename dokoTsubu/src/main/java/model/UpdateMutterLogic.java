package model;

import dao.MuttersDAO;

public class UpdateMutterLogic {

	public void execute(String strId, String text) {
		//idをintに変換
		int id = Integer.parseInt(strId);
		MuttersDAO dao = new MuttersDAO();
		dao.update(id, text);
	}
}