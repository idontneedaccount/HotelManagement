package com.example.hotel_management.entities;

import com.example.hotel_management.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email không thể để trống")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Mật khẩu không thể để trống")

    private String password;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Số điện thoại không thể để trống")
    @Column(name="phoneNumber",unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean active;

    private final LocalDate createdAt = LocalDate.now();
}
