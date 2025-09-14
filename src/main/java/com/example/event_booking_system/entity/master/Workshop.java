package com.example.event_booking_system.entity.master;

import com.example.event_booking_system.entity.BaseEntity;
import com.example.event_booking_system.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_workshop", indexes = {
        @Index(name = "idx_workshop_created_date", columnList = "createdDate"),
        @Index(name = "idx_workshop_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_workshop_deskripsi", columnList = "deskripsi"),
        @Index(name = "idx_workshop_kapasitas", columnList = "name"),
        @Index(name = "idx_workshop_status", columnList = "status"),
        @Index(name = "idx_workshop_harga", columnList = "harga"),
        @Index(name = "idx_workshop_judul", columnList = "judul")
})
public class Workshop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String deskripsi;

    @Column(nullable = false)
    @NotNull(message = "Harga tidak boleh negatif")
    private double harga;

    @Column(nullable = false)
    private Integer kapasitas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Builder.Default
    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}
