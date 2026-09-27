/*
 * Program Name: UserDAO
 * Language: Java
 * Function: Insert a user into USERS and select one user by NAME
 * Created: 2026-09-13
 * Last Updated: 2026-09-13
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.6
 * Memo: Phase 1 DokoTsubu3. Register INSERT unchanged. Login SELECT by NAME only.
 */

package dokotsubu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import dokotsubu.model.RegisterResult;
import dokotsubu.model.UserCredential;

@Repository
public class UserDAO {

    private final String jdbcUrl;
    private final String dbUsername;
    private final String dbPassword;

    public UserDAO(
            @Value("${dokotsubu.db.url}") String jdbcUrl,
            @Value("${dokotsubu.db.username}") String dbUsername,
            @Value("${dokotsubu.db.password}") String dbPassword) {
        this.jdbcUrl = jdbcUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public RegisterResult registerUser(String name, String hashedPassword) {
        String sql = "INSERT INTO USERS (NAME, PASS) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, name);
            pStmt.setString(2, hashedPassword);
            int result = pStmt.executeUpdate();
            if (result != 1) {
                return RegisterResult.DB_ERROR;
            }
            return RegisterResult.SUCCESS;
        } catch (SQLException e) {
            if (isDuplicateNameUniqueViolation(e)) {
                return RegisterResult.DUPLICATE;
            }
            return RegisterResult.DB_ERROR;
        }
    }

    public UserCredential findByName(String name) {
        String sql = "SELECT ID, NAME, PASS FROM USERS WHERE NAME = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new UserCredential(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("PASS"));
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private boolean isDuplicateNameUniqueViolation(SQLException e) {
        for (SQLException se = e; se != null; se = se.getNextException()) {
            if (matchesMysql1062NameUnique(se)) {
                return true;
            }
        }
        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            return isDuplicateNameUniqueViolation((SQLException) cause);
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
        if (!lower.contains("for key")) {
            return false;
        }
        return lower.contains("name");
    }
}
