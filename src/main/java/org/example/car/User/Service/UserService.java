package org.example.car.User.Service;

import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;

import java.util.List;

public class UserService {

    public static List<User> getAllUsers(){
        return UserRepository.getAllUsers();
    }

    public static void deleteUser(int userId){
        UserRepository.deleteUser(userId);
    }
}
