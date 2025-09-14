package com.example.event_booking_system.mapper.managementuser;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.request.UserRequestRecord;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User requestToEntity(UserRequestRecord request) {
        return User.builder()
                .username(request.nama().toUpperCase())
                .username(request.username().toLowerCase())
                .email(request.email().toLowerCase())
                .password(request.password())
                .status(request.status())
                .role(request.role())
                .build();
    }
}
