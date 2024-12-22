package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Modify this method to return Optional<User>
    Optional<User> findByUsername(String username);
}