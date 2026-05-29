package com.example.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc.model.User;

public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "userid SERIAL PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "age INT, " +
                "address TEXT, " +
                "qualification TEXT" +
                ")";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    public User createUser(User u) throws SQLException {
        String sql = "INSERT INTO users(username,password,age,address,qualification) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getAge());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getQualification());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setUserId(rs.getInt(1));
                }
            }
        }
        return u;
    }

    public boolean updateUser(User u) throws SQLException {
        String sql = "UPDATE users SET username=?, password=?, age=?, address=?, qualification=? WHERE userid=?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getAge());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getQualification());
            ps.setInt(6, u.getUserId());
            boolean updated = ps.executeUpdate() > 0;
            conn.commit();
            return updated;
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Failed to update user", e);
        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE userid=?";
        try{
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            boolean deleted = ps.executeUpdate() > 0;
            conn.commit();
            return deleted;
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Failed to retrieve users", e);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT userid, username, password, age, address, qualification FROM users ORDER BY userid";
        List<User> list = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userid"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setAge(rs.getInt("age"));
                u.setAddress(rs.getString("address"));
                u.setQualification(rs.getString("qualification"));
                list.add(u);
                
            }
            conn.commit();
        } catch (SQLException e) {
            list.clear();
            conn.rollback();
            System.out.println("Failed to retrieve users");
        }
        return list;
    }

    public List<User> getALLUserOldWayList() throws SQLException {
        String sql ="SELECT userid, username, password, age, address, qualification FROM users ORDER BY userid";
        List<User> list = new ArrayList<>();
        Connection conn1 = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
            conn1.setAutoCommit(false);
            stmt = conn1.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userid"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setAge(rs.getInt("age"));
                u.setAddress(rs.getString("address"));
                u.setQualification(rs.getString("qualification"));
                list.add(u);
                
            }
            rs.close();
            conn1.commit();
        } catch (SQLException e) {
            list.clear();
            conn.rollback();
            System.out.println("Failed to search users");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Postgres driver not found", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close statement");
                }
            }
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close connection");
                }
            }
        }
        return list;
    }

    public void createUserOldWay(User u) throws SQLException {
        List<User> list = new ArrayList<>();
        Connection conn1 = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "12345");
            conn1.setAutoCommit(false);
            stmt = conn1.createStatement();
            String sql = "INSERT INTO users(username,password,age,address,qualification) VALUES ('" + u.getUserName() + "','" + u.getPassword() + "'," + u.getAge() + ",'" + u.getAddress() + "','" + u.getQualification() + "')";
            int count = stmt.executeUpdate(sql);
            System.out.println("Record inserted " + count + " row(s)");
            conn1.commit();
        } catch (SQLException e) {
            list.clear();
            conn.rollback();
            System.out.println("Failed to search users");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Postgres driver not found", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close statement");
                }
            }
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close connection");
                }
            }
        }
    }
}
