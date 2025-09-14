package com.example.event_booking_system.entity;

import com.example.event_booking_system.entity.master.Booking;
import com.example.event_booking_system.model.enums.Role;
import com.example.event_booking_system.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_user", indexes = {
        @Index(name = "idx_user_created_date", columnList = "createdDate"),
        @Index(name = "idx_user_modified_date", columnList = "modifiedDate"),
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "nama"),
        @Index(name = "idx_user_status", columnList = "status"),
        @Index(name = "idx_user_role", columnList = "role")
})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "username tidak boleh kosong")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "email tidak boleh kosong")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String token;
    private LocalDateTime expiredTokenAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

}
