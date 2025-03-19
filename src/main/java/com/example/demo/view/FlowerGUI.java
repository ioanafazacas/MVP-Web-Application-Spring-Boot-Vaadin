package com.example.demo.view;


import com.example.demo.presenter.FlowerPresenter;
import com.example.demo.presenter.InterfaceGUI.IFlowerGUI;
import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@PageTitle("Flowers")
@Route(value = "/flower")
@Component
@UIScope
public class FlowerGUI extends VerticalLayout implements IFlowerGUI {
    private FlowerPresenter presenter;
    private TextField nameField = new TextField("Name");
    private TextField priceField = new TextField("Price");
    private TextField imageField = new TextField("URL Imagine");
    private TextField quantityField = new TextField("Quantity");
    private Button addButton = new Button("Add Flower");
    private Button deleteButton = new Button("Delete Flower");
    private Button updateButton = new Button("Update Flower");
    private Grid<FlowerDto> floareGrid = new Grid<>(FlowerDto.class);
    private ComboBox<ShopDto> shopComboBox = new ComboBox<>("Select a shop in witch to add the nre flower");
    private ComboBox<FlowerDto> flowerComboBox = new ComboBox<>("Select a flower for delete or update");
    private ComboBox<String> colorComboBox = new ComboBox<>("Select a color for the flower");

    public FlowerGUI(FlowerPresenter presenter){
        this.presenter=presenter;
        presenter.init_IFlowerGUI(this);

        addButton.addClickListener(e -> presenter.addFlower());
        deleteButton.addClickListener(e -> presenter.deleteFlower());
        updateButton.addClickListener(e -> presenter.updateFlower());

        setColorComboBox();
        flowerComboBox.setWidthFull();
        shopComboBox.setWidthFull();

        floareGrid.addComponentColumn(flowerDto -> {
            Image image = new Image(flowerDto.imagePath(), "No Image");
            image.setWidth("100px");  // Setează dimensiunea imaginii
            image.setHeight("100px");
            return image;
        }).setHeader("Image");

        add(new Button("Reimprospatare", e -> presenter.showAllFlowers()));
        add(nameField, priceField, imageField,shopComboBox,quantityField,colorComboBox,
                addButton,flowerComboBox,deleteButton,updateButton, floareGrid);

    }
    private void setColorComboBox(){
        Collection<String> items = new ArrayList<>();
        items.add("RED");
        items.add("WHITE");
        items.add("PINK");
        items.add("YELLOW");
        colorComboBox.setItems(items);
    }

    @Override
    public void setGrid(List<FlowerDto> flowers) {
        floareGrid.setItems(flowers);
    }

    @Override
    public void setComboBoxFlowers(List<FlowerDto> flowers) {
        flowerComboBox.setItems(flowers);
    }

    @Override
    public void setComboBoxShops(List<ShopDto> shops) {
        shopComboBox.setItems(shops);
    }

    @Override
    public ShopDto getSelectedShop() {
        return shopComboBox.getValue();
    }

    @Override
    public FlowerDto getSelectedFlower() {
        return flowerComboBox.getValue();
    }

    @Override
    public String getName() {
        return nameField.getValue();
    }

    @Override
    public String getColor() {
        return colorComboBox.getValue() ;
    }

    @Override
    public float getPrice() {
        return Float.parseFloat(priceField.getValue());
    }

    @Override
    public int getQuantity() {
        return Integer.parseInt(quantityField.getValue());
    }

    @Override
    public String getImagePath() {
        return imageField.getValue();
    }
}

