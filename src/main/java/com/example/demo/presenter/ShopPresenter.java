package com.example.demo.presenter;

import com.example.demo.model.FlowerShop;
import com.example.demo.model.repository.FlowerFromShopRepository;
import com.example.demo.model.repository.FlowerShopRepository;
import com.example.demo.presenter.InterfaceGUI.IShopGUI;
import com.example.demo.presenter.dto.ShopDto;
import com.example.demo.presenter.dto.mapper.ShopMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopPresenter {
    private final FlowerShopRepository repository;
    private final FlowerFromShopRepository flowerFromShopRepository;
    private IShopGUI view;
    public ShopPresenter(FlowerShopRepository repository, FlowerFromShopRepository flowerFromShopRepository){
        this.repository=repository;
        this.flowerFromShopRepository=flowerFromShopRepository;
    }
    public void init_IShopGUI(IShopGUI view){
        this.view=view;
        this.showAllShops();
    }
    public void addShop(){
        String name = view.getName();
        String address = view.getAddress();

        FlowerShop shop = FlowerShop.builder()
                .name(name)
                .address(address)
                .build();
        repository.save(shop);
    }
    public void deleteShop(){
        ShopDto shopDto = view.getSelectedShop();
        FlowerShop shop = repository.findByNameAndAddress(shopDto.name(), shopDto.address());
        repository.delete(shop);
        flowerFromShopRepository.deleteByShopId(shop.getId());
    }
    public void updateShop(){
        String address = view.getAddress();
        ShopDto shopDto = view.getSelectedShop();

        if(address==null || address.equals(""))
            address= shopDto.address();
        repository.update(shopDto.name(),address);

    }
    public void showAllShops(){
        List<ShopDto> shops = ShopMapper.shopListEntityToDto(repository.findAll());
        view.setGrid(shops);
        view.setComboBox(shops);
    }
}
