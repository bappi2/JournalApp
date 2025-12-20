package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(ObjectId id);

    List<User> getAllUsers();

    User updateUser(ObjectId id, User user);

    void deleteUser(ObjectId id);

    Optional<User> findByUserName(String userName);
}
