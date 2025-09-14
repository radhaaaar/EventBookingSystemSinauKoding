package com.example.event_booking_system.model.request;

import com.example.event_booking_system.entity.master.Booking;
import com.example.event_booking_system.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WorkshopRequestRecord(String id,
                                    @NotBlank(message = "jududl tidak boleh ksoong") String judul,
                                    @NotBlank(message="deskripsi tidak boleh kosong")String deskripsi,
                                    @NotNull(message = "harga tidak boleh kosong") Double harga,
                                    @NotNull(message = "kapasitas tidak boleh kosong") Integer kapasitas,
                                    @NotNull(message = "status tidak boleh kosong") Status status,
                                    @NotNull(message = "booking tidak boleh kosong") List<Booking> bookings
) {
}
