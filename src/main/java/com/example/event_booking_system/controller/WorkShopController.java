package com.example.event_booking_system.controller;

import com.example.event_booking_system.model.app.BaseResponse;
import com.example.event_booking_system.model.request.WorkshopFilterRecord;
import com.example.event_booking_system.model.request.WorkshopRequestRecord;
import com.example.event_booking_system.service.master.WorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workshop")
@RequiredArgsConstructor
public class WorkShopController {

    private final WorkshopService workshopService;

    @PostMapping("save")
    public BaseResponse<?> save(@RequestBody WorkshopRequestRecord request) {
        workshopService.add(request);
        return BaseResponse.ok("Data workshop berhasil disimpan", null);
    }

    @PostMapping("edit")
    public BaseResponse<?> edit(@RequestBody WorkshopRequestRecord request) {
        workshopService.edit(request);
        return BaseResponse.ok("Data workshop berhasil diubah", null);
    }

    @PostMapping("find-all")
    public BaseResponse<?> findAll(@PageableDefault(direction = Sort.Direction.DESC, sort = "modifiedDate") Pageable pageable,
                                   @RequestBody WorkshopFilterRecord filterRequest) {
        return BaseResponse.ok(null, workshopService.findAll(filterRequest, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok(null, workshopService.findById(id));
    }
}
