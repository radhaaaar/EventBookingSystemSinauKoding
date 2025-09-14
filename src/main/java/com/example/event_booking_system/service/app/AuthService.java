package com.example.event_booking_system.service.app;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.request.LoginRequestRecord;

public interface AuthService {

    SimpleMap login(LoginRequestRecord request);

    void logout(User userLoggedIn);
}
