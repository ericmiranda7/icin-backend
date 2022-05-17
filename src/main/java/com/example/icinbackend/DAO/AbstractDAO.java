package com.example.icinbackend.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractDAO<T> implements DAO<T> {
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public AbstractDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
