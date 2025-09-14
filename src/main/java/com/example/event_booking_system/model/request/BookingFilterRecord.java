package com.example.event_booking_system.model.request;

import com.example.event_booking_system.model.enums.Status;

public record BookingFilterRecord(String userId,
                                  String workshopId,
                                  Status status) {
}
