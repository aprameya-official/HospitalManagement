package com.example.HospitalManagement.service;

import com.example.HospitalManagement.model.User;
import com.example.HospitalManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user wrapped in an Optional
        Optional<User> userOptional = userRepository.findByUsername(username);

        // If the user is not found, throw an exception
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // Get the user from the Optional
        User user = userOptional.get();

        // Return the user details (ensure this is the correct hashed password)
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())  // Ensure this is the hashed password
                .roles(user.getRole())  // Ensure the role is set correctly
                .build();
    }
}