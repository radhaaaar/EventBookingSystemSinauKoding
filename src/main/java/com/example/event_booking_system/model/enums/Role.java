package com.example.event_booking_system.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("User"),
    ADMIN("Admin");

    private final String label;

    Role(String label) {
        this.label = label;
    }
}
