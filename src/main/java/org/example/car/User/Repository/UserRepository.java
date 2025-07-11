package org.example.car.User.Repository;

import org.example.car.DBConnector;
import org.example.car.User.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public static List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                int id = rs.getInt("id");
                String full_name = rs.getString("full_name");
                String password_hash = rs.getString("password_hash");
                boolean is_admin = rs.getBoolean("is_admin");

                User user = new User(id,full_name,password_hash,is_admin);
                users.add(user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    public static boolean deleteUser(int user_id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user_id);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static User getUserById(int id) throws SQLException {
        String query = "select * from users where id = ?";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int userId = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String passwordHash = rs.getString("password_hash");
                boolean isAdmin = rs.getBoolean("is_admin");

                return new User(userId, fullName, passwordHash, isAdmin);
            }
        }


        return null;
    }

    public static boolean save(User user) {
        String sql = "INSERT INTO users (full_name, password_hash, is_admin) VALUES (?, ?, ?)";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getFull_name());
            ps.setString(2, user.getPassword_hash());
            ps.setBoolean(3, user.isAdmin());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User findByFullNameAndPassword(String fullName, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE full_name = ? AND password_hash = ?";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, fullName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("full_name");
                String pass = rs.getString("password_hash");
                boolean isAdmin = rs.getBoolean("is_admin");
                return new User(id, name, pass, isAdmin);
            }
        }
        return null;
    }

    public static boolean existsByFullName(String fullName) {
        String sql = "SELECT 1 FROM users WHERE full_name = ? LIMIT 1";
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
