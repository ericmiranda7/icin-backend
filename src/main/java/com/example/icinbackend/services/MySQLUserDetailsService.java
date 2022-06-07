package com.example.icinbackend.services;

import com.example.icinbackend.DAO.UserDAO;
import com.example.icinbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MySQLUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Autowired
    public MySQLUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDAO.getOneById(username);
    }
}