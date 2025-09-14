package com.example.event_booking_system.repository.master;


import com.example.event_booking_system.entity.master.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkshopRepository extends JpaRepository<Workshop, String>, JpaSpecificationExecutor<Workshop> {
    boolean existsByJudul(String judul);
    boolean existsByJudulAndIdNot(String judul, String id);
}
