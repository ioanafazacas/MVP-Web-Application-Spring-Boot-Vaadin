package com.example.demo.presenter;

import com.example.demo.model.Flower;
import com.example.demo.model.FlowerFromFlowerShop;
import com.example.demo.model.FlowerShop;
import com.example.demo.model.repository.FlowerFromShopRepository;
import com.example.demo.model.repository.FlowerRepository;
import com.example.demo.model.repository.FlowerShopRepository;
import com.example.demo.presenter.InterfaceGUI.IFlowerFromShopGUI;
import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;
import com.example.demo.presenter.dto.mapper.FlowerMapper;
import com.example.demo.presenter.dto.mapper.ShopMapper;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlowerFromShopPresenter {
    private final FlowerRepository flowerRepository;
    private final FlowerShopRepository shopRepository;
    private final FlowerFromShopRepository flowerFromShopRepository;

    private IFlowerFromShopGUI view;
    //nu imi place aceasta modificare
    public FlowerFromShopPresenter(FlowerRepository flowerRepository, FlowerShopRepository shopRepository, FlowerFromShopRepository flowerFromShopRepository){
        this.flowerRepository = flowerRepository;
        this.shopRepository=shopRepository;
        this.flowerFromShopRepository=flowerFromShopRepository;
    }
    public void init_IFlowerFromShopGUI(IFlowerFromShopGUI view){
        this.view=view;
        this.showAllFlowers();
    }
    public void showAllFlowers(){
        List<FlowerDto> flowers = FlowerMapper.flowerListEntityToDto(flowerRepository.findAll());
        List<ShopDto> shops = ShopMapper.shopListEntityToDto(shopRepository.findAll());
        //view.setGrid(flowers);
        view.setComboBoxFlowers(flowers);
        view.setComboBoxShops(shops);
    }
    public void addFlowerToShop(){

            String color = view.getColor();
            int quantity = view.getQuantity();
            FlowerDto flowerDto = view.getSelectedFlower();
            ShopDto shopDto = view.getSelectedShop();

            // Caută floarea după nume, dacă există deja
            Flower flower = flowerRepository.findByName(flowerDto.name());
            FlowerShop shop = shopRepository.findByNameAndAddress(shopDto.name(),shopDto.address());

            // Creare relație între Flower și FlowerShop
            FlowerFromFlowerShop flowerEntry = new FlowerFromFlowerShop();
            flowerEntry.setFlower(flower);
            flowerEntry.setFlowerShop(shop);
            flowerEntry.setColor(color);
            flowerEntry.setQuantity(quantity);

            flowerFromShopRepository.save(flowerEntry);

            Notification.show("Floarea a fost adăugată în florărie!", 3000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

    }

    public void filterFlowers() {
        String disponibility = view.getDisponibility();;
        String color = view.getColorFilter();
        List<Flower> flowers= new ArrayList<>();
        if(color!=null){

            for(FlowerFromFlowerShop flowerToShop: flowerFromShopRepository.findByColor(color)){
                flowers.add(flowerToShop.getFlower());
            }
        }
        if(disponibility!=null){
        if(disponibility.equals("DISPONIBIL")){

            for(FlowerFromFlowerShop flowerToShop: flowerFromShopRepository.findAvailableFlowers()){
                flowers.add(flowerToShop.getFlower());
            }
        }else{
            for(FlowerFromFlowerShop flowerToShop: flowerFromShopRepository.findOutOfStockFlowers()){
                flowers.add(flowerToShop.getFlower());
            }
        }}
        view.setGrid(new ArrayList<>());
        view.setGrid(FlowerMapper.flowerListEntityToDto(flowers));


        Notification.show("Tabel filtrat cu succes!", 3000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
    public void searchFlower(){
        String name = view.getName();

    }


}
