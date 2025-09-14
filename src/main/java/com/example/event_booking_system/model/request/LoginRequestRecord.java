package com.example.event_booking_system.model.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecord(
        @NotBlank String username,
        @NotBlank String password
) {}
