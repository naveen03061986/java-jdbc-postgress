package com.example.jdbc.dao;

import com.example.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setInt(3, u.getAge());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getQualification());
            ps.setInt(6, u.getUserId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE userid=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT userid, username, password, age, address, qualification FROM users ORDER BY userid";
        List<User> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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
        }
        return list;
    }
}
