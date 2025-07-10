package com.example.hotel_management.services;

import com.example.hotel_management.dtos.LoginRequest;
import com.example.hotel_management.dtos.RegisterRequest;
import com.example.hotel_management.dtos.Response;
import com.example.hotel_management.dtos.UserDTO;
import com.example.hotel_management.entities.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    Response getOwnAccountDetails();

    User getCurrentUser();

    Response updateOwnAccountDetails(UserDTO userDTO);

    Response deleteOwnAccount();

    Response getMyBookingHistory();

}
