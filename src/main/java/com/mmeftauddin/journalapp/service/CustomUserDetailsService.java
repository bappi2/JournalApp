package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.repository.UserRepository;
import com.mmeftauddin.journalapp.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .roles(user.get().getRoles().toArray(new String[0]))
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}