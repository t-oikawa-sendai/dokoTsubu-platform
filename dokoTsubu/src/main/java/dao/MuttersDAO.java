/*
 * DATE		: 2024-06-15
 * LASTUPDATE:2025-11-14
 * Author:Takashi Oikawa
 * Function:
 * Memo:mysqlからh2に変更 再度MySQLに変更
*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MuttersDAO {
    // データベース接続に使用する情報（MySQL）
    // Update:20260404 useAffectedRows=false で UPDATE の戻りを「一致行数」に寄せる（内容未変更で affected 0 になり result==1 を満たさない事象の抑制）
    private final String JDBC_URL = "jdbc:mysql://localhost/dokoTsubu?useAffectedRows=false";
    private final String DB_USER = "root";
    private final String DB_PASS = "7358";

    // つぶやき取得
    public List<Mutter> findAll() {
        List<Mutter> mutterList = new ArrayList<>();
        // JDBCドライバを読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
        
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            // SELECT文の準備
            String sql = "SELECT m.ID, u.NAME, m.TEXT " +
                         "FROM MUTTERS m " +
                         "JOIN USERS u ON m.USER_ID = u.ID " +
                         "ORDER BY m.ID DESC";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            // SELECTを実行
            ResultSet rs = pStmt.executeQuery();

            // SELECT文の結果をArrayListに格納
            while (rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("NAME");
                String text = rs.getString("TEXT");
                Mutter mutter = new Mutter(id, userName, text);
                mutterList.add(mutter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mutterList;
    }

    // つぶやき登録
    public boolean create(Mutter mutter) {
        // JDBCドライバを読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }

        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            // INSERT文の準備(idは自動連番なので指定しなくてよい）
            String sql = "INSERT INTO MUTTERS(USER_ID, TEXT) VALUES(?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            
            // INSERT文中の「?」に使用する値を設定しSQLを完成
            pStmt.setInt(1, mutter.getUserId());
            pStmt.setString(2, mutter.getText());

            // INSERT文を実行（resultには追加された行数が代入される）
            int result = pStmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // つぶやき検索
    public List<Mutter> search(String keyword) {
        List<Mutter> mutterList = new ArrayList<>();
        // JDBCドライバを読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
        
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            // SELECT文の準備
            String sql = "SELECT m.ID, u.NAME, m.TEXT " +
                         "FROM MUTTERS m " +
                         "JOIN USERS u ON m.USER_ID = u.ID " +
                         "WHERE m.TEXT LIKE ? " +
                         "ORDER BY m.ID DESC";
            
            PreparedStatement pStmt = conn.prepareStatement(sql);
            // SELECT文中の「?」に使用する値を設定しSQLを完成
            pStmt.setString(1, "%" + keyword + "%");

            // SELECTを実行
            ResultSet rs = pStmt.executeQuery();

            // SELECT文の結果をArrayListに格納
            while (rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("NAME");
                String text = rs.getString("TEXT");
                Mutter mutter = new Mutter(id, userName, text);
                mutterList.add(mutter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mutterList;
    }
    
    // つぶやき編集
    public boolean update(int id, String text) {
        // JDBCドライバを読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
        
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            // UPDATE文の準備
            String sql = "UPDATE MUTTERS SET TEXT = ? WHERE ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            // UPDATE文中の「?」に使用する値を設定しSQLを完成
            pStmt.setString(1, text);
            pStmt.setInt(2, id);

            // UPDATE文を実行
            int result = pStmt.executeUpdate();
            // Update:20260404 WHERE ID=? で 1 行のみ想定。一致 1 件なら成功（上記 URL で一致行ベースの戻り値）
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // つぶやき削除
    public boolean delete(int id) {
        // JDBCドライバを読み込む
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした", e);
        }
        
        // データベース接続
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            // DELETE文の準備
            String sql = "DELETE FROM MUTTERS WHERE ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            // DELETE文中の「?」に使用する値を設定しSQLを完成
            pStmt.setInt(1, id);
            // DELETE文を実行
            int result = pStmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}