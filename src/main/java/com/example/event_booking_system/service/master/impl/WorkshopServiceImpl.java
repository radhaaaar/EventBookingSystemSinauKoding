package com.example.event_booking_system.service.master.impl;


import com.example.event_booking_system.entity.master.Workshop;
import com.example.event_booking_system.model.request.WorkshopFilterRecord;
import com.example.event_booking_system.model.request.WorkshopRequestRecord;
import com.example.event_booking_system.repository.master.WorkshopRepository;
import com.example.event_booking_system.repository.master.BookingRepository;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.builder.CustomBuilder;
import com.example.event_booking_system.model.app.AppPage;
import com.example.event_booking_system.service.master.WorkshopService;
import com.example.event_booking_system.util.FilterUtil;
import com.example.event_booking_system.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void add(WorkshopRequestRecord request) {
        validasiMandatory(request);

        if (workshopRepository.existsByJudul(request.judul())) {
            throw new RuntimeException("Judul workshop [" + request.judul() + "] sudah digunakan");
        }

        Workshop workshop = Workshop.builder()
                .judul(request.judul())
                .deskripsi(request.deskripsi())
                .harga(request.harga())
                .kapasitas(request.kapasitas())
                .status(request.status())
                .build();

        workshopRepository.save(workshop);
    }

    @Override
    public void edit(WorkshopRequestRecord request) {
        validasiMandatory(request);

        Workshop existing = workshopRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Data workshop tidak ditemukan"));

        if (workshopRepository.existsByJudulAndIdNot(request.judul(), request.id())) {
            throw new RuntimeException("Judul workshop [" + request.judul() + "] sudah digunakan");
        }

        existing.setJudul(request.judul());
        existing.setDeskripsi(request.deskripsi());
        existing.setHarga(request.harga());
        existing.setKapasitas(request.kapasitas());
        existing.setStatus(request.status());

        workshopRepository.save(existing);
    }

    @Override
    public AppPage<SimpleMap> findAll(WorkshopFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<Workshop> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("judul", filterRequest.judul(), builder);
        FilterUtil.builderConditionNotBlankLike("deskripsi", filterRequest.deskripsi(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);

        Page<Workshop> page = workshopRepository.findAll(builder.build(), pageable);

        List<SimpleMap> list = page.stream().map(w -> {
            // Hitung sisa kapasitas dengan cara sederhana
            int jumlahBooking = bookingRepository.countByWorkshopAndStatus(w, Status.CONFIRM);
            int sisaKapasitas = w.getKapasitas() - jumlahBooking;

            return SimpleMap.createMap()
                    .add("id", w.getId())
                    .add("judul", w.getJudul())
                    .add("deskripsi", w.getDeskripsi())
                    .add("harga", w.getHarga())
                    .add("kapasitas", w.getKapasitas())
                    .add("sisaKapasitas", sisaKapasitas)
                    .add("status", w.getStatus().getLabel());
        }).toList();

        return AppPage.create(list, pageable, page.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        Workshop w = workshopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data workshop tidak ditemukan"));

        // Hitung sisa kapasitas
        int jumlahBooking = bookingRepository.countByWorkshopAndStatus(w, Status.CONFIRM);
        int sisaKapasitas = w.getKapasitas() - jumlahBooking;

        return SimpleMap.createMap()
                .add("id", w.getId())
                .add("judul", w.getJudul())
                .add("deskripsi", w.getDeskripsi())
                .add("harga", w.getHarga())
                .add("kapasitas", w.getKapasitas())
                .add("sisaKapasitas", sisaKapasitas)
                .add("status", w.getStatus().getLabel());
    }

    private void validasiMandatory(WorkshopRequestRecord request) {
        if (request.judul() == null || request.judul().isEmpty()) {
            throw new RuntimeException("Judul tidak boleh kosong");
        }
        if (request.deskripsi() == null || request.deskripsi().isEmpty()) {
            throw new RuntimeException("Deskripsi tidak boleh kosong");
        }
        if (request.harga() < 0) {
            throw new RuntimeException("Harga tidak boleh negatif");
        }
        if (request.kapasitas() <= 0) {
            throw new RuntimeException("Kapasitas harus lebih dari 0");
        }
        if (request.status() == null) {
            throw new RuntimeException("Status tidak boleh kosong");
        }
    }
}
