package com.pas.repository;


import com.pas.model.IdTrait;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
