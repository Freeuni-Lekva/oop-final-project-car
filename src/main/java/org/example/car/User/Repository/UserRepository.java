package org.example.car.User.Repository;

import org.example.car.DBConnector;
import org.example.car.User.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public List<User> getAllUsers(){
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

    public void deleteUser(int user_id){
        String sql = "DELETE FROM users WHERE id = ?";

        try(
                Connection conn = DBConnector.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ){
            ps.setInt(1, user_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
