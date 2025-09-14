package com.example.event_booking_system.controller.app;

import com.example.event_booking_system.model.app.BaseResponse;
import com.example.event_booking_system.model.request.LoginRequestRecord;
import com.example.event_booking_system.model.request.UserRequestRecord;
import com.example.event_booking_system.service.app.AuthService;
import com.example.event_booking_system.service.app.impl.UserLoggedInConfig;
import com.example.event_booking_system.service.managementuser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody LoginRequestRecord request) {
        return BaseResponse.ok(null, authService.login(request));
    }

    @PostMapping("register")
    public BaseResponse<?> register(@RequestBody UserRequestRecord request) {
        userService.add(request);
        return BaseResponse.ok("Registrasi berhasil", null);
    }

    @GetMapping("logout")
    public BaseResponse<?> logout(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        var userLoggedIn = userLoggedInConfig.getUser();
        authService.logout(userLoggedIn);
        return BaseResponse.ok("Berhasil logout", null);
    }
}
