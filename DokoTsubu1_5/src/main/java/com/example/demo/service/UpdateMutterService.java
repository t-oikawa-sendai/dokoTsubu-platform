package com.example.demo.service;

import com.example.demo.dao.MuttersDAO;

public class UpdateMutterService {
	public void execute(String strId, String text) {
		//idをintに変換
		int id = Integer.parseInt(strId);
		MuttersDAO dao = new MuttersDAO();
		dao.update(id, text);
	}
}