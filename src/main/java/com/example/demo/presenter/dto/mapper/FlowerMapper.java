package com.example.demo.presenter.dto.mapper;

import com.example.demo.model.Flower;
import com.example.demo.presenter.dto.FlowerDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowerMapper {
    public static FlowerDto flowerEntityToDto(Flower flower){
        return FlowerDto.builder()
                .name(flower.getName())
                .price(flower.getPrice())
                .imagePath(flower.getImagePath())
                .build();
    }
    public static List<FlowerDto> flowerListEntityToDto(List<Flower> flowers){
        return flowers.stream().
                map(flower -> flowerEntityToDto(flower)).toList();
    }
    public static Flower flowerDtoToEntity(FlowerDto flowerDto){
        return Flower.builder().
                name(flowerDto.name()).
                price(flowerDto.price()).
                imagePath(flowerDto.imagePath()).
                build();
    }
}
