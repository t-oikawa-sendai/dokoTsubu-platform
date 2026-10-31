package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.MuttersDAO;
import com.example.demo.model.Mutter;

@Service
public class PostMutterService {
	public boolean execute(Mutter mutter) {
		MuttersDAO dao = new MuttersDAO();
		return dao.create(mutter);
	}
}
