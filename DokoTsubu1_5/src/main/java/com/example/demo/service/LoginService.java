package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.UsersDAO;
import com.example.demo.model.User;

@Service
public class LoginService {
	  // ユーザー情報の存在チェック
	  public User find(User user) {
	      UsersDAO dao = new UsersDAO();
	      
	      return dao.findUser(user);     
	  }
}
