package com.example.event_booking_system.controller;

import com.example.event_booking_system.model.app.BaseResponse;
import com.example.event_booking_system.model.request.UserFilterRecord;
import com.example.event_booking_system.model.request.UserRequestRecord;
import com.example.event_booking_system.service.managementuser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("save")
    public BaseResponse<?> save(@RequestBody UserRequestRecord request) {
        userService.add(request);
        return BaseResponse.ok("Data berhasil disimpan", null);
    }

    @PostMapping("edit")
    public BaseResponse<?> edit(@RequestBody UserRequestRecord request) {
        userService.edit(request);
        return BaseResponse.ok("Data berhasil diubah", null);
    }

    @PostMapping("find-all")
    public BaseResponse<?> findAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody UserFilterRecord filterRequest) {
        return BaseResponse.ok(null, userService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, userService.findById(id));
    }
}
