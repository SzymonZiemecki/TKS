package com.pas.repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pas.model.IdTrait;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IRepositoryImpl<T extends IdTrait> implements IRepository<T> {

    private Map<UUID, T> objects = Collections.synchronizedMap(new HashMap<>());

    private static final Logger log = LoggerFactory.getLogger(IRepositoryImpl.class);

    private static final String ENTITY_NOT_FOUND_MESSAGE="Entity with given ID doesn't exist";

    @Override
    public synchronized T add(T entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (objects.containsKey(entity.getId())) {
            log.info("Entity with provided id already exists in application context");
        } else {
            objects.put(entity.getId(), entity);
        }
        return entity;
    }

    @Override
    public synchronized void delete(UUID id) {
        if (!objects.containsKey(id)) {
            log.info("Entity with provided id isnt present in app context");
        } else {
            T entity = findById(id).orElse(null);
            objects.remove(entity.getId());
        }
    }

    @Override
    public synchronized void delete(T entity) {
        if (!objects.containsValue(entity)) {
            objects.remove(entity);
        } else {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    @SneakyThrows
    public synchronized T update(UUID id, T entity) {
        if (!findById(id).isPresent()) {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE);
        } else {
            objects.replace(id, entity);
        }
        return entity;
    }

    @Override
    public boolean exists(String id) {
        return objects.containsKey(UUID.fromString(id));
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(objects.get(id));
    }

    @Override
    public List<T> findAll() {
        return objects.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return objects.size();
    }

    public List<T> filter(Predicate<T> predicate) {
        return objects.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
