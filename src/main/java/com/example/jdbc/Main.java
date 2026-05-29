package com.example.jdbc;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.example.jdbc.dao.UserDao;
import com.example.jdbc.model.User;
import com.example.jdbc.util.DBUtil;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            UserDao dao = new UserDao(conn);
            runCli(dao);
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runCli(UserDao dao) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1) Create user");
            System.out.println("2) Edit user");
            System.out.println("3) Delete user");
            System.out.println("4) List all users");
            System.out.println("5) Exit");
            System.out.println("6) List all users (old way)");
            System.out.println("7) Create user (old way)");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        createUser(dao, sc);
                        break;
                    case "2":
                        editUser(dao, sc);
                        break;
                    case "3":
                        deleteUser(dao, sc);
                        break;
                    case "4":
                        listUsers(dao);
                        break;
                    case "6":
                        oldWayOflistUsers(dao);
                        break;
                    case "7":
                        createUserOldWay(dao, sc);
                        break;
                    case "5":
                        System.out.println("Goodbye");
                        return;
                    default:
                        System.out.println("Unknown choice");
                }
            } catch (Exception e) {
                System.err.println("Operation failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void createUser(UserDao dao, Scanner sc) throws Exception {
        System.out.print("User name: ");
        String name = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Address: ");
        String addr = sc.nextLine().trim();
        System.out.print("Qualification: ");
        String qual = sc.nextLine().trim();
        User u = new User(name, pass, age, addr, qual);
        dao.createUser(u);
        System.out.println("Created user with id=" + u.getUserId());
    }

    private static void createUserOldWay(UserDao dao, Scanner sc) throws Exception {
        System.out.print("User name: ");
        String name = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Address: ");
        String addr = sc.nextLine().trim();
        System.out.print("Qualification: ");
        String qual = sc.nextLine().trim();
        User u = new User(name, pass, age, addr, qual);
        dao.createUserOldWay(u);
    }

    private static void editUser(UserDao dao, Scanner sc) throws Exception {
        System.out.print("User id to edit: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("New user name: ");
        String name = sc.nextLine().trim();
        System.out.print("New password: ");
        String pass = sc.nextLine().trim();
        System.out.print("New age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("New address: ");
        String addr = sc.nextLine().trim();
        System.out.print("New qualification: ");
        String qual = sc.nextLine().trim();
        User u = new User(name, pass, age, addr, qual);
        u.setUserId(id);
        boolean ok = dao.updateUser(u);
        System.out.println(ok ? "Updated" : "No user with that id");
    }

    private static void deleteUser(UserDao dao, Scanner sc) throws Exception {
        System.out.print("User id to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        boolean ok = dao.deleteUser(id);
        System.out.println(ok ? "Deleted" : "No user with that id");
    }

    private static void listUsers(UserDao dao) throws Exception {
        List<User> list = dao.getAllUsers();
        if (list.isEmpty()) {
            System.out.println("No users found");
            return;
        }
        for (User u : list) {
            System.out.println(u);
        }
    }

    private static void oldWayOflistUsers(UserDao dao) throws Exception {
        List<User> list = dao.getALLUserOldWayList();
        if (list.isEmpty()) {
            System.out.println("No users found");
            return;
        }
        for (User u : list) {
            System.out.println(u);
        }
    }
}
