package com.example.event_booking_system.service.master.impl;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.entity.master.Booking;
import com.example.event_booking_system.entity.master.Workshop;
import com.example.event_booking_system.model.request.BookingRequestRecord;
import com.example.event_booking_system.model.request.BookingFilterRecord;
import com.example.event_booking_system.repository.master.BookingRepository;
import com.example.event_booking_system.repository.master.WorkshopRepository;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.builder.CustomBuilder;
import com.example.event_booking_system.model.app.AppPage;
import com.example.event_booking_system.service.master.BookingService;
import com.example.event_booking_system.util.FilterUtil;
import com.example.event_booking_system.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final WorkshopRepository workshopRepository;

    @Override
    public void createBooking(BookingRequestRecord request, User userLoggedIn) {
        // Ambil data workshop
        Workshop workshop = workshopRepository.findById(request.workshopId())
                .orElseThrow(() -> new RuntimeException("Workshop tidak ditemukan"));

        // Cek status workshop
        if (workshop.getStatus() != Status.CONFIRM) {
            throw new RuntimeException("Workshop tidak tersedia untuk booking");
        }

        // Cek apakah user sudah booking workshop ini
        boolean sudahBooking = bookingRepository.existsByUserAndWorkshop(userLoggedIn, workshop);
        if (sudahBooking) {
            throw new RuntimeException("Anda sudah melakukan booking untuk workshop ini");
        }

        // Cek kapasitas
        int jumlahBookingSekarang = bookingRepository.countByWorkshopAndStatus(workshop, Status.CONFIRM);
        if (jumlahBookingSekarang >= workshop.getKapasitas()) {
            throw new RuntimeException("Kapasitas workshop sudah penuh");
        }

        // Buat booking baru
        Booking booking = Booking.builder()
                .tanggalPemesanan(LocalDateTime.now())
                .user(userLoggedIn)
                .workshop(workshop)
                .status(Status.CONFIRM)
                .build();

        bookingRepository.save(booking);
    }

    @Override
    public AppPage<SimpleMap> findMyBookings(User userLoggedIn, BookingFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Booking> builder = new CustomBuilder<>();

        // Filter berdasarkan user yang login
        FilterUtil.builderConditionNotNullEqual("user", userLoggedIn, builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);

        Page<Booking> page = bookingRepository.findAll(builder.build(), pageable);

        List<SimpleMap> list = page.stream().map(this::mapBookingToSimpleMap).toList();

        return AppPage.create(list, pageable, page.getTotalElements());
    }

    @Override
    public AppPage<SimpleMap> findAllBookings(BookingFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Booking> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        if (filterRequest.userId() != null && !filterRequest.userId().isEmpty()) {
            FilterUtil.builderConditionNotBlankEqual("user.id", filterRequest.userId(), builder);
        }
        if (filterRequest.workshopId() != null && !filterRequest.workshopId().isEmpty()) {
            FilterUtil.builderConditionNotBlankEqual("workshop.id", filterRequest.workshopId(), builder);
        }

        Page<Booking> page = bookingRepository.findAll(builder.build(), pageable);

        List<SimpleMap> list = page.stream().map(this::mapBookingToSimpleMap).toList();

        return AppPage.create(list, pageable, page.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking tidak ditemukan"));

        return mapBookingToSimpleMap(booking);
    }

    // Method helper untuk mapping
    private SimpleMap mapBookingToSimpleMap(Booking booking) {
        return SimpleMap.createMap()
                .add("id", booking.getId())
                .add("tanggalPemesanan", booking.getTanggalPemesanan())
                .add("status", booking.getStatus().getLabel())
                .add("userName", booking.getUser().getUsername())
                .add("userEmail", booking.getUser().getEmail())
                .add("workshopTitle", booking.getWorkshop().getJudul())
                .add("workshopHarga", booking.getWorkshop().getHarga());
    }
}
