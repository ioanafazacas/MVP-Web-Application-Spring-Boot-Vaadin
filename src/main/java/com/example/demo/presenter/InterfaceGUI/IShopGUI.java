package com.example.demo.presenter.InterfaceGUI;

import com.example.demo.presenter.dto.ShopDto;

import java.util.List;

public interface IShopGUI {
    void setGrid(List<ShopDto> shops);
    void setComboBox(List<ShopDto> shops);
    ShopDto getSelectedShop();
    String getName();
    String getAddress();
}
