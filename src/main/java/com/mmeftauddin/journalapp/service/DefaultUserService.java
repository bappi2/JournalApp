package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.User;
import com.mmeftauddin.journalapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // Let MongoDB generate ObjectId; just save the user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(ObjectId id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        if (user.getUserName() != null) {
            existing.setUserName(user.getUserName());
        }
        if (user.getPassword() != null) {
            existing.setPassword(user.getPassword());
        }

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
