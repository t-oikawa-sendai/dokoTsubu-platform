package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.MuttersDAO;
import com.example.demo.model.Mutter;

public class SearchMutterService {
	public List<Mutter> execute(String keyword) {
		MuttersDAO dao = new MuttersDAO();
		List<Mutter> mutterList = dao.search(keyword);
		return mutterList;
	}
}
