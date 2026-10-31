package com.example.demo.service;

import com.example.demo.dao.MuttersDAO;

public class DeleteMutterService {

	public void execute(String strId) {
		System.out.println(strId);
		//idをintに変換
		int id = Integer.parseInt(strId);
		MuttersDAO dao = new MuttersDAO();
		dao.delete(id);
	}
}