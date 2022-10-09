package com.pas.repository;

import com.pas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityExistsException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements IRepository<User> {

    private List<User> objects = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public void add(User entity) {
        if (!objects.contains(entity)) {
            objects.add(entity);
        } else {
            log.error("Entity already exists in database");
            throw new EntityExistsException("Entity already exists in database");
        }
    }

    @Override
    public void delete(String id) {
        User user = findById(id);
        if (objects.contains(user)) {
            objects.remove(user);
        } else {
            log.error("Entity already exists in database");
            throw new EntityExistsException("Entity already exists in database");
        }
    }

    @Override
    public void update(User UpdatedEntity) {
        if(exists(UpdatedEntity.getId().toString())){
            objects.remove(findById(UpdatedEntity.getId().toString()));
            objects.add(UpdatedEntity);
        }
    }

    @Override
    public User get(String id) {
        return findById(id);
    }

    @Override
    public boolean exists(String id) {
        return objects.contains(findById(id));
    }

    @Override
    public User findById(String id) {
        return objects.stream()
                .filter(user -> user.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public int size(){
        return objects.size();
    }
}
