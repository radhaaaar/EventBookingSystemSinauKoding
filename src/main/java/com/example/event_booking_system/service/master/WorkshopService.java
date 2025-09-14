package com.example.event_booking_system.service.master;

import com.example.event_booking_system.model.app.AppPage;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.request.WorkshopFilterRecord;
import com.example.event_booking_system.model.request.WorkshopRequestRecord;
import org.springframework.data.domain.Pageable;

public interface WorkshopService {

    void add(WorkshopRequestRecord request);

    void edit(WorkshopRequestRecord request);

    AppPage<SimpleMap> findAll(WorkshopFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}
