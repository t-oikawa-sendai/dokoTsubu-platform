package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.model.User;

public class UsersDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/dokoTsubu";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	// ユーザー登録
    public boolean registerUser(User user) {
    	// JDBCドライバを読み込む
    	try {
    	    Class.forName("org.h2.Driver");
    	} catch (ClassNotFoundException e) {
    	    throw new IllegalStateException("JDBCドライバを読み込めませんでした");
    	}

        // データベース接続
        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	String sql = "INSERT INTO users (name, pass) VALUES (?, ?);";
        	
        	PreparedStatement pStmt = conn.prepareStatement(sql);
        	pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());
            
            int result = pStmt.executeUpdate();
            if (result != 1) {
              return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
          }
          return true;
    }

    // 引数で受け取ったユーザー情報と一致するユーザーが存在するかチェック
    public User findUser(User user) {
    	// JDBCドライバを読み込む
    	try {
    	    Class.forName("org.h2.Driver");
    	} catch (ClassNotFoundException e) {
    	    throw new IllegalStateException("JDBCドライバを読み込めませんでした");
    	}
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	String sql = "SELECT * FROM users WHERE name = ? and pass = ?";
        	PreparedStatement pStmt = conn.prepareStatement(sql);
        	pStmt.setString(1, user.getName());
        	pStmt.setString(2, user.getPass());
        	
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next()) {
            	int id = rs.getInt("id");
            	String name = rs.getString("name");
            	String pass = rs.getString("pass");
                User findUser = new User(id, name, pass);
                return findUser;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;        }
    }
}