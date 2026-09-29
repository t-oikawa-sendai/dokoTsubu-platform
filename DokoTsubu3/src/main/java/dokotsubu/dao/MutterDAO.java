/*
 * Program Name: MutterDAO
 * Language: Java
 * Function: Select mutters in ID descending order, search mutter text, insert one mutter, and select, update, or delete one owned mutter
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor
 * Memo: Phase 1 DokoTsubu3 FR-004, FR-005, FR-006, FR-007, and FR-008. List and search also return USER_ID. Owned select, UPDATE, and DELETE always use ID and USER_ID.
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
                + "WHERE m.TEXT LIKE ? "
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
        String sql = "INSERT INTO MUTTERS(USER_ID, TEXT) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, userId);
            pStmt.setString(2, text);
            return pStmt.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public Mutter findByIdAndUserId(int id, int userId) {
        String sql = "SELECT m.ID, m.USER_ID, u.NAME, m.TEXT "
                + "FROM MUTTERS m "
                + "JOIN USERS u ON m.USER_ID = u.ID "
                + "WHERE m.ID = ? AND m.USER_ID = ?";
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
        String sql = "UPDATE MUTTERS SET TEXT = ? WHERE ID = ? AND USER_ID = ?";
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
        String sql = "DELETE FROM MUTTERS WHERE ID = ? AND USER_ID = ?";
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
