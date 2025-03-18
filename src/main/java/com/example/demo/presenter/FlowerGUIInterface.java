package com.example.demo.presenter;

import com.example.demo.model.Flower;

import java.util.List;

public interface FlowerGUIInterface {
    void showAllFlowers(List<Flower> flowers);
    void showFlower(Flower flower);
    String getName();
    String getColor();
    float getPrice();
    String getImagePath();
}
