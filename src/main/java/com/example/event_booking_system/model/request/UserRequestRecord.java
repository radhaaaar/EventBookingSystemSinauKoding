package com.example.event_booking_system.model.request;

import com.example.event_booking_system.entity.master.Booking;
import com.example.event_booking_system.model.enums.Role;
import com.example.event_booking_system.model.enums.Status;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserRequestRecord(String id, String nama, String username, String email, String password, Status status, Role role,
                                @NotNull(message = "Pemesanan tidak boleh kosong")List<Booking>bookings) {
}
