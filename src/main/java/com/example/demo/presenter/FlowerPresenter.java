package com.example.demo.presenter;

import com.example.demo.model.Flower;
import com.example.demo.model.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowerPresenter {
    @Autowired
    private FlowerRepository repository;
    private FlowerGUIInterface view;
    public FlowerPresenter(FlowerGUIInterface view){
        this.view = view;
    }
    public void showAllFlowers(){
        List<Flower> flowers = repository.findAll();
        view.showAllFlowers(flowers);
    }
    public void addFlower(){
        String name = view.getName();
        float price= view.getPrice();
        String imagePath= view.getImagePath();


        Flower flower= Flower.builder()
                .name(name)
                .price(price)
                .imagePath(imagePath)
                .build();
        repository.save(flower);
    }
}