/*
 * Program Name: MutterDAO
 * Language: Java
 * Function: Select mutters in ID descending order, search mutter text, insert one mutter, and select, update, or delete one owned mutter
 * Created: 2026-09-29
 * Last Updated: 2026-10-08
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3. Active rows require DELETED_AT IS NULL. Owned update and logical delete also require an active user.
 */

package dokotsubu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dokotsubu.model.Mutter;

@Repository
public class MutterDAO {

    private final String jdbcUrl;
    private final String dbUsername;
    private final String dbPassword;

    public MutterDAO(
            @Value("${dokotsubu.db.url}") String jdbcUrl,
            @Value("${dokotsubu.db.username}") String dbUsername,
            @Value("${dokotsubu.db.password}") String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public List<Mutter> findAll() {
        List<Mutter> mutterList = new ArrayList<>();
        String sql = "SELECT m.ID, m.USER_ID, u.NAME, m.TEXT "
                + "FROM MUTTERS m "
                + "JOIN USERS u ON m.USER_ID = u.ID "
                + "WHERE m.DELETED_AT IS NULL AND u.DELETED_AT IS NULL "
                + "ORDER BY m.ID DESC";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery()) {
            while (rs.next()) {
                mutterList.add(new Mutter(
                        rs.getInt("ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("TEXT")));
            }
            return mutterList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public List<Mutter> search(String keyword) {
        List<Mutter> mutterList = new ArrayList<>();
        String sql = "SELECT m.ID, m.USER_ID, u.NAME, m.TEXT "
                + "FROM MUTTERS m "
                + "JOIN USERS u ON m.USER_ID = u.ID "
                + "WHERE m.TEXT LIKE ? AND m.DELETED_AT IS NULL AND u.DELETED_AT IS NULL "
                + "ORDER BY m.ID DESC";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    mutterList.add(new Mutter(
                            rs.getInt("ID"),
                            rs.getInt("USER_ID"),
                            rs.getString("NAME"),
                            rs.getString("TEXT")));
                }
            }
            return mutterList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public boolean create(int userId, String text) {
        String sql = "INSERT INTO MUTTERS (USER_ID, TEXT, CREATED_AT, UPDATED_AT, DELETED_AT) "
                + "SELECT u.ID, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL "
                + "FROM USERS u WHERE u.ID = ? AND u.DELETED_AT IS NULL";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, text);
            pStmt.setInt(2, userId);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public Mutter findByIdAndUserId(int id, int userId) {
        String sql = "SELECT m.ID, m.USER_ID, u.NAME, m.TEXT "
                + "FROM MUTTERS m "
                + "JOIN USERS u ON m.USER_ID = u.ID "
                + "WHERE m.ID = ? AND m.USER_ID = ? AND m.DELETED_AT IS NULL AND u.DELETED_AT IS NULL";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, id);
            pStmt.setInt(2, userId);
            try (ResultSet rs = pStmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Mutter(
                        rs.getInt("ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("NAME"),
                        rs.getString("TEXT"));
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean update(int id, int userId, String text) {
        String sql = "UPDATE MUTTERS m "
                + "INNER JOIN USERS u ON m.USER_ID = u.ID "
                + "SET m.TEXT = ?, m.UPDATED_AT = CURRENT_TIMESTAMP "
                + "WHERE m.ID = ? AND m.USER_ID = ? AND m.DELETED_AT IS NULL AND u.DELETED_AT IS NULL";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, text);
            pStmt.setInt(2, id);
            pStmt.setInt(3, userId);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id, int userId) {
        String sql = "UPDATE MUTTERS m "
                + "INNER JOIN USERS u ON m.USER_ID = u.ID "
                + "SET m.DELETED_AT = CURRENT_TIMESTAMP, m.UPDATED_AT = CURRENT_TIMESTAMP "
                + "WHERE m.ID = ? AND m.USER_ID = ? AND m.DELETED_AT IS NULL AND u.DELETED_AT IS NULL";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, id);
            pStmt.setInt(2, userId);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
}
