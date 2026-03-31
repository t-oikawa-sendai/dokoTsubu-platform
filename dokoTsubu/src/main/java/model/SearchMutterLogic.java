package model;

import java.util.List;

import dao.MuttersDAO;

public class SearchMutterLogic {

	public List<Mutter> execute(String keyword) {
		MuttersDAO dao = new MuttersDAO();
		List<Mutter> mutterList = dao.search(keyword);
		return mutterList;
	}
}
