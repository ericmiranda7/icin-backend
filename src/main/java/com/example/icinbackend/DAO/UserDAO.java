package com.example.icinbackend.DAO;

import com.example.icinbackend.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDAO<User> {
    public UserDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User addOne(User user) {
        this.jdbcTemplate
                .update("INSERT INTO users (username, password) " +
                        "VALUES (?, ?)", user.getUsername(), user.getPassword());
        return user;
    }

    @Override
    public User getOneById(int id) {
        return null;
    }

    @Override
    public User getOneById(String id) {
        return this.jdbcTemplate
                .queryForObject("SELECT * FROM users WHERE username" +
                        " = ?", (rs, rowNum) -> {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setCust_id(rs.getInt(3));
                    user.setAdmin_id(rs.getInt(4));
                    user.setPassword(rs.getString(5));
                    return user;
                }, id);
    }
}
