package com.example.demo.presenter.dto;

import lombok.Builder;

@Builder
public record FlowerDto(
        String name,
        float price,
        String imagePath
) {
    @Override
    public String toString() {
        return name ;
    }
}
