package com.example.icinbackend.DAO;

public interface DAO<T> {
    T addOne(T t);

    T getOneById(int id);

    T getOneById(String id);
}
