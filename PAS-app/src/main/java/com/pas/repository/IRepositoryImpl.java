package com.pas.repository;

import com.pas.model.IdTrait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class IRepositoryImpl<T extends IdTrait> implements IRepository<T> {

    private Map<UUID, T> objects = Collections.synchronizedMap(new HashMap<>());
    private static final Logger log = LoggerFactory.getLogger(IRepositoryImpl.class);

    @Override
    public void add(T entity) {
        if (objects.containsKey(entity.getId())) {
            log.info("Entity with provided id already exists in application context");
        } else {
            objects.put(entity.getId(),entity);
        }
    }

    @Override
    public void delete(String id) {
        if (!objects.containsKey(UUID.fromString(id))){
            log.info("Entity with provided id isnt present in app context");
        } else {
            T entity = findById(id).get();
            objects.remove(entity.getId());
        }
    }

    @Override
    public void delete(T entity) {
        if (!objects.containsKey(entity.getId())){
            log.info("Entity with provided id isnt present in app context");
        } else {
            objects.remove(entity.getId());
        }
    }

    @Override
    public void update(T entity) {
        if (!objects.containsKey(entity.getId())) {
            log.info("Entity with provided id isnt present in app contex");
        } else {
            objects.put(entity.getId(),entity);
        }
    }

    @Override
    public boolean exists(String id) {
        return objects.containsKey(UUID.fromString(id));
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(objects.get(UUID.fromString(id)));
    }

    @Override
    public List<T> findAll() {
        return objects.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return objects.size();
    }
}
