package com.example.hotel_management.entities;

import com.example.hotel_management.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Số phòng phải lớn hơn 0")
    @Column(unique = true)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @DecimalMin(value = "0.1", message = "Giá phòng phải lớn hơn 0")
    private BigDecimal price;

    @Min(value = 1, message = "Sức chứa phải lớn hơn 0")
    private Integer capacity;

    private String description;

    private String imageUrl;
}
