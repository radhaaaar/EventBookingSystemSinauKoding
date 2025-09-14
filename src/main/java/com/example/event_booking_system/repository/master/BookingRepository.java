package com.example.event_booking_system.repository.master;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.entity.master.Booking;
import com.example.event_booking_system.entity.master.Workshop;
import com.example.event_booking_system.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String>, JpaSpecificationExecutor<Booking> {
    //hitung booking per workshop
    int countByWorkshopAndStatus(Workshop workshop, Status status);

    //cek user sudah booking atau belum
    boolean existsByUserAndWorkshop(User user, Workshop workshop);

    //ambil booking berdasarkan user
    List<Booking> findByUser(User user);

    //ambil booking berdasarkan workshop
    List<Booking> findByWorkshop(Workshop workshop);
}
