package com.example.demo.model;

import jakarta.persistence.*;

import java.awt.*;

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
    private Color color;
}