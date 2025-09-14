package com.example.event_booking_system.model.enums;

import lombok.Getter;

@Getter

public enum Status {

    CANCEL("Cancel"),
    CONFIRM("Confirm");

    private final String label;

    Status(String label) {
        this.label = label;
    }
}
