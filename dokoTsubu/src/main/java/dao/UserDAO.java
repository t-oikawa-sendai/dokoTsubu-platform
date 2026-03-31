/*
 * DATE		: 2024-06-15
 * LASTUPDATE:2025-11-07
 * Author:Takashi Oikawa
 * Function:
 * Memo:mysqlからh2に変更
*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost/dokoTsubu";		
//	private final String JDBC_URL = "jdbc:h2://localhost/dokoTsubu";	  //2025-11-07ADD
	private final String DB_USER = "root";
	private final String DB_PASS = "7358";

	// ユーザー登録
    public boolean registerUser(User user) {
    	// JDBCドライバを読み込む
    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");   
//    	    Class.forName("org.h2.Driver");      		//2025-11-07ADD
    	    
    	} catch (ClassNotFoundException e) {
    	    throw new IllegalStateException("JDBCドライバを読み込めませんでした");
    	}

        // データベース接続
        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	//SQL usersテーブルにnameとpassを挿入する
        	//SQL <プレースホルダ>?の部分に<User.java>のUserオブジェクトから取り出したnameとpassを入れる
        	String sql = "INSERT INTO users (name, pass) VALUES (?, ?);";
        	
        	/*
        	 * PreparedStatementは、
        	 * JavaのJDBC APIにおいて、パラメータを持つSQLステートメントを安全かつ効率的に実行するための仕組みです。
        	 * ?（疑問符）をプレースホルダとして使用し、後から値をバインド（設定）できる点が大きな特徴です。    	 */
        	PreparedStatement pStmt = conn.prepareStatement(sql);
        	pStmt.setString(1, user.getName());		//1番目の？にnameをセット
            pStmt.setString(2, user.getPass());		//2番目の？にpassをセット
            
            /*
             * executeUpdate()は、JavaのJDBC APIにおいて、
             * データベースのデータを更新（変更）するSQLステートメントを実行するためのメソッドです。
				主に以下の操作に使用されます。
				INSERT: 新しい行をテーブルに挿入する
				UPDATE: 既存の行のデータを更新する
				DELETE: 既存の行をテーブルから削除する
				これらの操作は、データベースのデータ内容を変更する「更新系」の処理です。
				戻り値:executeUpdate()メソッドは、**更新によって影響を受けた行の数（int型）**を返します。   */
            int result = pStmt.executeUpdate();
//            System.out.println("登録処理結果=" + result);	//Debug
            if (result != 1) {		//INSERTのSQL発行　更新できれば「1」が返る　1以外はエラー
              return false;
            }
//            System.out.println(result);	//Debug           
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
    	    Class.forName("com.mysql.cj.jdbc.Driver");	 
//    	    Class.forName("org.h2.Driver");      		//2025-11-07ADD    	    
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