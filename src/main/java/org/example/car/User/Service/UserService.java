package org.example.car.User.Service;

import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;
import org.example.car.User.Service.PasswordHashingService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public static List<User> getAllUsers(){
        return UserRepository.getAllUsers();
    }

    public static boolean deleteUser(int userId){
        return UserRepository.deleteUser(userId);
    }

    public static boolean save(String full_name, String password, boolean is_admin){
        if (UserRepository.existsByFullName(full_name)) {
            return false;
        }
        
        String hashedPassword = PasswordHashingService.hashPassword(password);
        User userToSave = new User(full_name, hashedPassword, is_admin);
        return UserRepository.save(userToSave);
    }

    public static User authenticate(String fullName, String password) throws SQLException {
        String hashedPassword = PasswordHashingService.hashPassword(password);
        return UserRepository.findByFullNameAndPassword(fullName, hashedPassword);
    }

    public static User getUserById(int userId) throws SQLException {
        return UserRepository.getUserById(userId);
    }

    public static List<User> searchUsersByName(String name) {
        return UserRepository.searchUsersByName(name);
    }
}
