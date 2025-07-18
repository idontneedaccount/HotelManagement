package com.example.hotel_management.controllers;

import com.example.hotel_management.dtos.Response;
import com.example.hotel_management.dtos.UserDTO;
import com.example.hotel_management.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/update")
    public ResponseEntity<Response> updateOwnAccount(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateOwnAccountDetails(userDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteOwnAccount(){
        return ResponseEntity.ok(userService.deleteOwnAccount());
    }

    @GetMapping("/account")
    public ResponseEntity<Response> getOwnAccountDetails(){
        return ResponseEntity.ok(userService.getOwnAccountDetails());
    }


    @GetMapping("/bookings")
    public ResponseEntity<Response> getMyBookingHistory(){
        return ResponseEntity.ok(userService.getMyBookingHistory());
    }
}
