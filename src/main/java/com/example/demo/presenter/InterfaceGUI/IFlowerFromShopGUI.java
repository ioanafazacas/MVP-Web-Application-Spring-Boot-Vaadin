package com.example.demo.presenter.InterfaceGUI;

import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;

import java.util.List;

public interface IFlowerFromShopGUI {
    void setGrid(List<FlowerDto> flowers);
    void setComboBoxFlowers(List<FlowerDto> flowers);
    void setComboBoxShops(List<ShopDto> shops);
    ShopDto getSelectedShop();
    FlowerDto getSelectedFlower();
    String getName();
    String getColor();
    String getColorFilter();
    String getDisponibility();
    int getQuantity();

}
