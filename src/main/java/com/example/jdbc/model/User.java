package com.example.jdbc.model;

public class User {
    private int userId;
    private String userName;
    private String password;
    private int age;
    private String address;
    private String qualification;

    public User() {}

    public User(String userName, String password, int age, String address, String qualification) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.address = address;
        this.qualification = qualification;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}
