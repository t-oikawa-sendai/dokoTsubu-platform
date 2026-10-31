package com.example.demo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	  private int id; // ID
	  private String name; // ユーザー名
	  private String pass; // パスワード

	  // 登録・存在チェック用コンストラクタ  
	  public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	  }
}