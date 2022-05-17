package com.example.icinbackend.tests.integration;

import com.example.icinbackend.DAO.UserDAO;
import com.example.icinbackend.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
public class UserDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void userIsAddedSuccessfully() {
        UserDAO userDAO = new UserDAO(this.jdbcTemplate);
        User user = new User();
        user.setUsername("jojo");
        user.setPassword("someHas");
        User savedUser = userDAO.addOne(user);
        assertThat(savedUser.getUsername().equals("jojo")).isTrue();
    }
}
