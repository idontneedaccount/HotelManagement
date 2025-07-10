package com.example.hotel_management.services.Impl;

import com.example.hotel_management.dtos.*;
import com.example.hotel_management.entities.Booking;
import com.example.hotel_management.entities.User;
import com.example.hotel_management.enums.UserRole;
import com.example.hotel_management.exceptions.InvalidCredentialException;
import com.example.hotel_management.exceptions.NotFoundException;
import com.example.hotel_management.repositories.BookingRepository;
import com.example.hotel_management.repositories.UserRepository;
import com.example.hotel_management.security.JwtUtils;
import com.example.hotel_management.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.CUSTOMER;
        if(registerRequest.getRole()!=null){
            role = registerRequest.getRole();
        }
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .active(Boolean.TRUE)
                .build();
        userRepository.save(user);
        log.info("User registered successfully: {}", user.getEmail());
        return Response.builder()
                .status(200)
                .message("User registered successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("Email Not Found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Password doesn't match");
        }

        String token = jwtUtils.generateJwtToken(user.getEmail());


        return Response.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .isActive(user.getActive())
                .expirationTime("6 months")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOList = modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .users(userDTOList)
                .build();
    }

    @Override
    public Response getOwnAccountDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));


        log.info("Inside getOwnAccountDetails user email is {}", email);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
    }

    @Override
    public Response updateOwnAccountDetails(UserDTO userDTO) {
        User existingUser = getCurrentUser();
        log.info("Inside update user");

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getFirstName() != null) existingUser.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null) existingUser.setLastName(userDTO.getLastName());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("user updated successfully")
                .build();
    }

    @Override
    public Response deleteOwnAccount() {
        User user = getCurrentUser();
        userRepository.delete(user);

        return Response.builder()
                .status(200)
                .message("user deleted successfully")
                .build();
    }

    @Override
    public Response getMyBookingHistory() {

        User user = getCurrentUser();

        List<Booking> bookingList = bookingRepository.findByUserId(user.getId());


        List<BookingDTO> bookingDTOList = modelMapper.map(bookingList, new TypeToken<List<BookingDTO>>(){}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .bookings(bookingDTOList)
                .build();

    }
}
