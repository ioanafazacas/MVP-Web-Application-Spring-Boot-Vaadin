package com.example.demo.presenter;

import com.example.demo.model.Flower;
import com.example.demo.model.FlowerFromFlowerShop;
import com.example.demo.model.FlowerShop;
import com.example.demo.model.repository.FlowerFromShopRepository;
import com.example.demo.model.repository.FlowerRepository;
import com.example.demo.model.repository.FlowerShopRepository;
import com.example.demo.presenter.InterfaceGUI.IFlowerGUI;
import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;
import com.example.demo.presenter.dto.mapper.FlowerMapper;
import com.example.demo.presenter.dto.mapper.ShopMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlowerPresenter {
    private final FlowerRepository flowerRepository;
    private final FlowerShopRepository shopRepository;
    private final FlowerFromShopRepository flowerFromShopRepository;
    private IFlowerGUI view;
    public FlowerPresenter(FlowerRepository flowerRepository, FlowerShopRepository shopRepository, FlowerFromShopRepository flowerFromShopRepository){
        this.flowerRepository = flowerRepository;
        this.shopRepository=shopRepository;
        this.flowerFromShopRepository=flowerFromShopRepository;
    }
    public void init_IFlowerGUI(IFlowerGUI view){
        this.view=view;
        this.showAllFlowers();
    }
    public void showAllFlowers(){
        List<FlowerDto> flowers = FlowerMapper.flowerListEntityToDto(flowerRepository.findAll());
        List<ShopDto> shops = ShopMapper.shopListEntityToDto(shopRepository.findAll());
        view.setGrid(flowers);
        view.setComboBoxFlowers(flowers);
        view.setComboBoxShops(shops);
    }
    public void addFlower(){
        String name = view.getName();
        float price= view.getPrice();
        String imagePath= view.getImagePath();
        int quantity = view.getQuantity();
        String color = view.getColor();
        ShopDto shopDto = view.getSelectedShop();

        FlowerShop shop = shopRepository.findByNameAndAddress(shopDto.name(),shopDto.address());

        Flower flower= Flower.builder()
                .name(name)
                .price(price)
                .imagePath(imagePath)
                .build();
        flowerRepository.save(flower);
        Flower newFlower = flowerRepository.findByName(name);

        FlowerFromFlowerShop flowerFromFlowerShop = new FlowerFromFlowerShop();
        flowerFromFlowerShop.setFlowerShop(shop);
        flowerFromFlowerShop.setFlower(newFlower);
        flowerFromFlowerShop.setQuantity(quantity);
        flowerFromFlowerShop.setColor(color);

        flowerFromShopRepository.save(flowerFromFlowerShop);
    }
    public void deleteFlower(){
        FlowerDto flowerDto = view.getSelectedFlower();
        Flower flower = flowerRepository.findByName(flowerDto.name());
        flowerRepository.delete(flower);
        flowerFromShopRepository.deleteByFlowerId(flower.getId());
    }
    public void updateFlower(){
        FlowerDto flowerDto = view.getSelectedFlower();
        Flower flower = flowerRepository.findByName(flowerDto.name());
        float price = view.getPrice();
        String imagePath = view.getImagePath();
        //if(price==null)
        if(imagePath == null || imagePath.equals("")){
            imagePath=flower.getImagePath();
        }
        flowerRepository.update(flower.getName(),price,imagePath);
    }
}