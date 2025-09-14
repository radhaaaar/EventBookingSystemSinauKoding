package com.example.event_booking_system.service.managementuser.impl;


import com.example.event_booking_system.builder.CustomBuilder;
import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.app.AppPage;
import com.example.event_booking_system.model.app.SimpleMap;
import com.example.event_booking_system.model.enums.Role;
import com.example.event_booking_system.model.enums.Status;
import com.example.event_booking_system.model.request.UserFilterRecord;
import com.example.event_booking_system.model.request.UserRequestRecord;
import com.example.event_booking_system.repository.managementuser.UserRepository;
import com.example.event_booking_system.service.managementuser.UserService;
import com.example.event_booking_system.util.FilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void add(UserRequestRecord request) {
        validasiMandatory(request);

        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }

        var user = User.builder()
                .username(request.nama())
                .email(request.email().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .status(request.status() != null ? request.status() : Status.CONFIRM)
                .role(request.role() != null ? request.role() : Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public void edit(UserRequestRecord request) {
        validasiMandatory(request);

        var userExisting = userRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));

        if (userRepository.existsByEmailAndIdNot(request.email().toLowerCase(), request.id())) {
            throw new RuntimeException("Email [" + request.email() + "] sudah digunakan");
        }

        userExisting.setUsername(request.nama());
        userExisting.setEmail(request.email().toLowerCase());
        if (request.password() != null && !request.password().isEmpty()) {
            userExisting.setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.status() != null) {
            userExisting.setStatus(request.status());
        }
        if (request.role() != null) {
            userExisting.setRole(request.role());
        }

        userRepository.save(userExisting);
    }

    @Override
    public AppPage<SimpleMap> findAll(UserFilterRecord filterRequest, Pageable pageable) {
        CustomBuilder<User> builder = new CustomBuilder<>();

        FilterUtil.builderConditionNotBlankLike("nama", filterRequest.nama(), builder);
        FilterUtil.builderConditionNotBlankLike("email", filterRequest.email(), builder);
        FilterUtil.builderConditionNotNullEqual("status", filterRequest.status(), builder);
        FilterUtil.builderConditionNotNullEqual("role", filterRequest.role(), builder);

        Page<User> listUser = userRepository.findAll(builder.build(), pageable);

        List<SimpleMap> listData = listUser.stream().map(user -> {
            return SimpleMap.createMap()
                    .add("id", user.getId())
                    .add("nama", user.getUsername())
                    .add("email", user.getEmail())
                    .add("role", user.getRole().getLabel())
                    .add("status", user.getStatus().getLabel());
        }).toList();

        return AppPage.create(listData, pageable, listUser.getTotalElements());
    }

    @Override
    public SimpleMap findById(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Data user tidak ditemukan"));
        return SimpleMap.createMap()
                .add("id", user.getId())
                .add("nama", user.getUsername())
                .add("email", user.getEmail())
                .add("status", user.getStatus().getLabel())
                .add("role", user.getRole().getLabel());
    }

    private void validasiMandatory(UserRequestRecord request) {
        if (request.nama() == null || request.nama().isEmpty()) {
            throw new RuntimeException("Nama tidak boleh kosong");
        }
        if (request.email() == null || request.email().isEmpty()) {
            throw new RuntimeException("Email tidak boleh kosong");
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new RuntimeException("Password tidak boleh kosong");
        }
    }
}
