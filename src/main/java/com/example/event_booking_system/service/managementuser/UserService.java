package com.example.event_booking_system.service.managementuser;


import com.example.event_booking_system.model.request.UserFilterRecord;
import com.example.event_booking_system.model.request.UserRequestRecord;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.app.AppPage;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void add(UserRequestRecord request);

    void edit(UserRequestRecord request);

    AppPage<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable);

    SimpleMap findById(String id);
}