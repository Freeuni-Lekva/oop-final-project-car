package org.example.car.User.Model;

public class User {
    private int id;
    private String full_name;
    private String password_hash;
    private boolean admin;

    public User(int id, String full_name, String password_hash, boolean is_admin){
        this.id = id;
        this.full_name = full_name;
        this.password_hash = password_hash;
        this.admin = is_admin;
    }

    public User(String full_name, String password_hash, boolean is_admin){
        this.full_name = full_name;
        this.password_hash = password_hash;
        this.admin = is_admin;
    }

    public int getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public boolean isAdmin() {
        return admin;
    }
}
