package model;

import dao.MuttersDAO;

public class DeleteMutterLogic {

	public void execute(String strId) {
		System.out.println(strId);
		//idをintに変換
		int id = Integer.parseInt(strId);
		MuttersDAO dao = new MuttersDAO();
		dao.delete(id);
	}

}
