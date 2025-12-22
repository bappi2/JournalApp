package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

    User updateUser(String id, User user);

    void deleteUser(String id);

    Optional<User> findByUsername(String username);
}
