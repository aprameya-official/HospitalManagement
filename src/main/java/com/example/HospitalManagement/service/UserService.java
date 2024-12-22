package com.example.HospitalManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.HospitalManagement.model.User;
import com.example.HospitalManagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User registerUser(String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    // Fetch all users (for admin dashboard)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);  // Assuming you're using a repository to access the database
    }

    // Find user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // Returns Optional<User>
    }

    // Update user information
    public User updateUser(Long userId, String username, String password, String role) {
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(username);  // Update username if needed
            existingUser.setRole(role);  // Update role

            // If password is provided, update it after encoding
            if (password != null && !password.isEmpty()) {
                String encodedPassword = passwordEncoder.encode(password);
                existingUser.setPassword(encodedPassword);
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Delete a user
    public boolean deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}