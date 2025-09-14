package com.example.event_booking_system.model.request;

import com.example.event_booking_system.model.enums.Status;

public record WorkshopFilterRecord (String judul,
                                    String deskripsi,
                                    Status status){
}
