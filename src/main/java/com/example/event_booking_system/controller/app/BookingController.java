package com.example.event_booking_system.controller.app;

import com.example.event_booking_system.model.app.BaseResponse;
import com.example.event_booking_system.model.request.BookingFilterRecord;
import com.example.event_booking_system.model.request.BookingRequestRecord;
import com.example.event_booking_system.service.app.impl.UserLoggedInConfig;
import com.example.event_booking_system.service.master.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("create")
    public BaseResponse<?> createBooking(@RequestBody BookingRequestRecord request,
                                         @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        bookingService.createBooking(request, userLoggedInConfig.getUser());
        return BaseResponse.ok("Booking berhasil dibuat", null);
    }

    @PostMapping("my-bookings")
    public BaseResponse<?> findMyBookings(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                          @RequestBody BookingFilterRecord filterRequest,
                                          @AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        return BaseResponse.ok(null, bookingService.findMyBookings(userLoggedInConfig.getUser(), filterRequest, pageable));
    }

    @PostMapping("find-all")
    public BaseResponse<?> findAllBookings(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                           @RequestBody BookingFilterRecord filterRequest) {
        return BaseResponse.ok(null, bookingService.findAllBookings(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, bookingService.findById(id));
    }
}
