package com.carrental.auth;

import com.carrental.db.DbUtil;

import java.sql.*;

public final class AuthService {
    private static final String DB = DbUtil.URL;

    /**
     * Hard-coded admin login: admin / admin123
     * Normal users are looked up in the existing users table.
     */
    public static User login(String name, String pass) {
        if (name.equals("admin") && pass.equals("admin123")) {
            return new User(0, "admin", true);
        }
        // existing table → users(id, username, password)
        try (Connection c = DriverManager.getConnection(DB);
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id, password FROM users WHERE username = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString("password").equals(pass)) {
                    return new User(rs.getInt("id"), name, false);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;            // bad credentials
    }
}
