/*
 * Program Name: MutterDAO
 * Language: Java
 * Function: Select all mutters joined to user names in ID descending order
 * Created: 2026-09-29
 * Last Updated: 2026-09-29
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-004. List query only. DB settings match UserDAO.
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
        String sql = "SELECT m.ID, u.NAME, m.TEXT "
                + "FROM MUTTERS m "
                + "JOIN USERS u ON m.USER_ID = u.ID "
                + "ORDER BY m.ID DESC";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
                PreparedStatement pStmt = conn.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery()) {
            while (rs.next()) {
                mutterList.add(new Mutter(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("TEXT")));
            }
            return mutterList;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
