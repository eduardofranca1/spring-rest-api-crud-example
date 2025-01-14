package com.example.api.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.api.entities.User;
import com.example.api.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return this.repository.save(user);
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findById(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    public User update(String id, User user) {

        User userDB = this.findById(id);

        // userDB.setName(user.getName());
        // userDB.setEmail(user.getEmail());
        // this.repository.save(userDB);

        BeanUtils.copyProperties(user, userDB, "id", "createdAt", "updatedAt");
        this.repository.save(userDB);

        return userDB;
    }

    public void deleteById(String id) {
        User user = this.findById(id);
        this.repository.deleteById(user.getId());
    }
}
