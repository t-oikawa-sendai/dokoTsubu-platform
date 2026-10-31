package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.UsersDAO;
import com.example.demo.model.User;

@Service
public class RegisterUserService {
	// ユーザー情報の登録
    public boolean execute(User user) {
        UsersDAO dao = new UsersDAO();
        // ユーザー登録処理
        return dao.registerUser(user);
    }
}