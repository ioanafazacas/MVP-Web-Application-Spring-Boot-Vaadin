package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flower_from_flower_shop")
public class FlowerFromFlowerShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_flower", nullable = false)
    private Flower flower;
    @ManyToOne
    @JoinColumn(name = "id_shop", nullable = false)
    private FlowerShop flowerShop;

    private int quantity;
    private String color;
}