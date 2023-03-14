package com.tks.infrastructure;

import com.tks.IdTrait;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface Repository<T>{
    T add(T entity);
    void delete(UUID id);
    void delete(T entity);
    T update(UUID id, T entity);
    boolean exists(String id);
    Optional<T> findById(UUID id);
    List<T> findAll();
    int size();
}
