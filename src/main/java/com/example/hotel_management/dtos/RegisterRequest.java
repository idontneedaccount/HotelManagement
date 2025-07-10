package com.example.hotel_management.dtos;

import com.example.hotel_management.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Họ tên là bắt buộc")
    private String firstName;

    @NotBlank(message = "Họ tên là bắt buộc")
    private String lastName;

    @NotBlank(message = "Email là bắt buộc")
    private String email;

    @NotBlank(message = "Số điện thoại là bắt buộc")
    private String phoneNumber;

    private UserRole role;

    @NotBlank(message = "Mật khẩu là bắt buộc")
    private String password;
}
