package com.likesmeverywell.Webshop.service;

import com.likesmeverywell.Webshop.model.User;
import com.likesmeverywell.Webshop.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authUserRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    public Optional<User> findById(@PathVariable Long id) {
        return authUserRepository.findById(id);
    }

    public User updateUser(@PathVariable Long id, User userDetails) {
        return authUserRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            user.setRole(userDetails.getRole());
            return authUserRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(@PathVariable Long id) {
        authUserRepository.deleteById(id);
    }

}
