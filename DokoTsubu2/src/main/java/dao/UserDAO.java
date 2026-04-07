/*
 * プログラム名: UserDAO
 * 機能概要: ユーザーの登録およびログイン照会のためのデータベースアクセスを提供する。
 * 動作条件: MySQL に接続可能であること。users テーブルが存在し、NAME に UNIQUE 制約があること。
 * その他記載事項: registerUser の戻り値は int 定数。1062 かつ NAME 用 UNIQUE キー重複のみ REGISTER_DUPLICATE、それ以外は REGISTER_DB_ERROR。
 * Date:2026/04/01
 * Author: Takashi Oikawa
 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import model.User;

public class UserDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost/dokoTsubu";		
//	private final String JDBC_URL = "jdbc:h2://localhost/dokoTsubu";	  //2025-11-07ADD
	private final String DB_USER = "root";
	private final String DB_PASS = "7358";

	// registerUser() の結果コード（新規ファイルを増やさず、呼び出し元で分岐できるようにする）
	public static final int REGISTER_OK = 1;
	public static final int REGISTER_DUPLICATE = 2;
	public static final int REGISTER_DB_ERROR = 3;

	// ユーザー登録
    public int registerUser(User user) {
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
              return REGISTER_DB_ERROR;
            }
//            System.out.println(result);	//Debug           
            return REGISTER_OK;
        } catch (SQLException e) {
            e.printStackTrace();
            if (isDuplicateNameUniqueViolation(e)) {
            	return REGISTER_DUPLICATE;
            }
            return REGISTER_DB_ERROR;
          }
    }

    /**
     * MySQL error code 1062（Duplicate entry）を最優先し、
     * users.NAME の UNIQUE 違反とみなせる場合のみ true。
     * 他の制約違反（1062 でも別キー等）は false。
     */
    private boolean isDuplicateNameUniqueViolation(SQLException e) {
    	for (SQLException se = e; se != null; se = se.getNextException()) {
    		if (matchesMysql1062NameUnique(se)) {
    			return true;
    		}
    	}
    	Throwable c = e.getCause();
    	if (c instanceof SQLException) {
    		return isDuplicateNameUniqueViolation((SQLException) c);
    	}
    	return false;
    }

    private boolean matchesMysql1062NameUnique(SQLException se) {
    	if (se.getErrorCode() != 1062) {
    		return false;
    	}
    	String msg = se.getMessage();
    	if (msg == null) {
    		return false;
    	}
    	String lower = msg.toLowerCase(Locale.ROOT);
    	if (!lower.contains("duplicate entry")) {
    		return false;
    	}
    	// MySQL: Duplicate entry '...' for key '...' — NAME 用 UNIQUE のキー名に name が含まれる想定
    	if (!lower.contains("for key")) {
    		return false;
    	}
    	return lower.contains("name");
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