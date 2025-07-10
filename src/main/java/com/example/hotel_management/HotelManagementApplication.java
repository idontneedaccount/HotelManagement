package com.example.hotel_management;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class HotelManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(HotelManagementApplication.class, args);
    }

}
