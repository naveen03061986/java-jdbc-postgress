package com.example.jdbc.util;

import com.example.jdbc.dao.UserDao;
import com.example.jdbc.model.User;

import java.sql.Connection;

public class InsertSampleUsers {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            UserDao dao = new UserDao(conn);
            for (int i = 1; i <= 10; i++) {
                User u = new User("user" + i, "pass" + i, 20 + i, "Address " + i, "Degree" + i);
                dao.createUser(u);
                System.out.println("Inserted: " + u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
