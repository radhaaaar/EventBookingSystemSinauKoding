package com.example.event_booking_system.service.master;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.request.BookingRequestRecord;
import com.example.event_booking_system.model.request.BookingFilterRecord;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.app.AppPage;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    void createBooking(BookingRequestRecord request, User userLoggedIn);

    AppPage<SimpleMap> findMyBookings(User userLoggedIn, BookingFilterRecord filterRequest, Pageable pageable);

    AppPage<SimpleMap> findAllBookings(BookingFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}
