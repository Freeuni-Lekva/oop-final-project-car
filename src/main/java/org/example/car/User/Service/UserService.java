package org.example.car.User.Service;

import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;
import org.example.car.User.Service.PasswordHashingService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {

    public static List<User> getAllUsers(){
        return UserRepository.getAllUsers();
    }

    public static void deleteUser(int userId){
        UserRepository.deleteUser(userId);
    }

    public static boolean save(User user){
        String hashedPassword = PasswordHashingService.hashPassword(user.getPassword_hash());
        User userToSave = new User(user.getFull_name(), hashedPassword, user.is_admin());
        return UserRepository.save(userToSave);
    }

    public static User authenticate(String fullName, String password) {
        return UserRepository.findByFullNameAndPassword(fullName, password);
    }
}
