package com.example.hotel_management.security;

import com.example.hotel_management.entities.User;
import com.example.hotel_management.exceptions.NotFoundException;
import com.example.hotel_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public AuthUser loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new NotFoundException("Người dùng không tồn tại"));
        return AuthUser.builder()
                .user(user)
                .build();
    }
}
