package com.example.demo.presenter.dto;

import lombok.Builder;

@Builder
public record ShopDto(
        String name,
        String address
) {
    @Override
    public String toString() {
        return name ;
    }
}
