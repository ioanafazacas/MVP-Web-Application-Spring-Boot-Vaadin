package com.example.demo.presenter.InterfaceGUI;

import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;

import java.util.List;

public interface IFlowerGUI {
    void setGrid(List<FlowerDto> flowers);
    void setComboBoxFlowers(List<FlowerDto> flowers);
    FlowerDto getSelectedFlower();
    String getName();
    float getPrice();
    String getImagePath();
}
