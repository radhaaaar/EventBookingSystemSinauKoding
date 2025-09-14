package com.example.event_booking_system.model.request;

import com.example.event_booking_system.model.enums.Role;
import com.example.event_booking_system.model.enums.Status;

public record UserFilterRecord(
        String nama,
        String email,
        String username,
        Status status,
        Role role
) {
}
