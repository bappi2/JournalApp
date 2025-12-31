package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.controller.ChangePasswordRequest;
import com.mmeftauddin.journalapp.entity.User;
import com.mmeftauddin.journalapp.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("secureUserService")
public class SecureUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public SecureUserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String id, User user) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
    @Override
    public User changePassword(ChangePasswordRequest request, Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Not authenticated");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));

        // 1) current password must match
        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong current password");
        }

        // 2) new + confirmation must match
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }

        // 3) save new password (hashed)
        user.setPassword(encoder.encode(request.getNewPassword()));
        User saved = userRepository.save(user);

        // OPTIONAL: don’t leak hash back to caller
        saved.setPassword(null);

        return saved;
    }
}
