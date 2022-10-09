package com.pas.repository;


public interface IRepository<T> {
    void add(T entity);
    void delete(String id);
    void update(T entity);
    T get(String id);
    boolean exists(String id);
    T findById(String id);
}
