package com.example.demo.presenter.dto.mapper;

import com.example.demo.model.FlowerShop;
import com.example.demo.presenter.dto.ShopDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopMapper {
    public static ShopDto shopEntityToDto(FlowerShop shop){
        return ShopDto.builder().
                name(shop.getName()).
                address(shop.getAddress())
                .build();
    }
    public static FlowerShop shopDtoToEntoty(ShopDto shopDto){
        return FlowerShop.builder().
                name(shopDto.name()).
                address(shopDto.address()).
                build();
    }
    public static List<ShopDto> shopListEntityToDto(List<FlowerShop> shops){
        return shops.stream()
                .map(shop -> shopEntityToDto(shop)).toList();
    }
}
