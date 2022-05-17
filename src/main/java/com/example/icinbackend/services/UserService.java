package com.example.icinbackend.services;

import com.example.icinbackend.DAO.UserDAO;
import com.example.icinbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashPassword);
        return this.userDAO.addOne(user);
    }
}
