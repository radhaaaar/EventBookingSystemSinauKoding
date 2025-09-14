package com.example.event_booking_system.entity.master;

import com.example.event_booking_system.entity.BaseEntity;
import com.example.event_booking_system.entity.User;
import com.example.event_booking_system.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_booking", indexes = {
        @Index(name = "idx_booking_created_date", columnList = "createdDate"),
        @Index(name = "idx_booking_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_booking_tanggalPemesanan", columnList = "tanggalPemesanan"),
        @Index(name = "idx_booking_kapasitas", columnList = "name"),
        @Index(name = "idx_booking_status", columnList = "status"),
        @Index(name = "idx_booking_user_id", columnList = "user_id"),
        @Index(name = "idx_booking_workshop_id", columnList = "workshop_id")
})
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private LocalDateTime tanggalPemesanan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}

