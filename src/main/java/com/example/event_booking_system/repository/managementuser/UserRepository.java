package com.example.event_booking_system.repository.managementuser;

import com.example.event_booking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, String id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
}
