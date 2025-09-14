package com.example.event_booking_system.service.app.impl;

import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.request.LoginRequestRecord;
import com.example.event_booking_system.repository.managementuser.UserRepository;
import com.example.event_booking_system.service.app.AuthService;
import com.example.event_booking_system.service.app.ValidatorService;
import com.example.event_booking_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidatorService validatorService;

    @Override
    public SimpleMap login(LoginRequestRecord request) {
        validatorService.validator(request);

        var user = userRepository.findByUsername(request.username().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Username atau password salah"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Username atau password salah");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        user.setExpiredTokenAt(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        SimpleMap result = new SimpleMap();
        result.put("token", token);
        return result;
    }

    @Override
    public void logout(User userLoggedIn) {
        userLoggedIn.setToken(null);
        userLoggedIn.setExpiredTokenAt(null);
        userRepository.save(userLoggedIn);
    }
}

