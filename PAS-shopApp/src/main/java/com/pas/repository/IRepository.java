package com.pas.repository;


import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    void add(T entity);

    void delete(String id);

    void delete(T entity);

    void update(T entity);

    boolean exists(String id);

    Optional<T> findById(String id);

    List<T> findAll();

    int size();
}
