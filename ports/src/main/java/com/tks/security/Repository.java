package com.tks.security;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T>{
    T add(T entity);
    void delete(UUID id);
    void delete(T entity);
    T update(UUID id, T entity);
    boolean exists(String id);
    T findById(UUID id);
    List<T> findAll();
    int size();
}
