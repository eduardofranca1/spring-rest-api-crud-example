package com.example.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.api.entities.User;
import com.example.api.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    public User update(String id, User user) {
        User userDB = findById(id);
        BeanUtils.copyProperties(user, userDB, "id", "createdAt", "updatedAt");
        repository.save(userDB);
        return userDB;
    }

    public void deleteById(String id) {
        User user = findById(id);
        repository.deleteById(user.getId());
    }
}
