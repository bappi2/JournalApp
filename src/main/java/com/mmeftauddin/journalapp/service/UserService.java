package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.controller.ChangePasswordRequest;
import com.mmeftauddin.journalapp.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(String id);

    List<User> getAllUsers();

    void deleteUser(String id);

    Optional<User> findByUsername(String username);
    User changePassword(ChangePasswordRequest request, Authentication authentication);
}
