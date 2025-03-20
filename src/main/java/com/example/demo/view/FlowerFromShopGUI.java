package com.example.demo.view;

import com.example.demo.presenter.FlowerFromShopPresenter;
import com.example.demo.presenter.InterfaceGUI.IFlowerFromShopGUI;
import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@PageTitle("Flowers From Shop")
@Route(value = "/flower_from_shop")
@Component
@UIScope
public class FlowerFromShopGUI extends VerticalLayout implements IFlowerFromShopGUI {
    private FlowerFromShopPresenter presenter;
    private TextField nameField = new TextField("Name");
    private TextField quantityField = new TextField("Quantity");
    private Button addButton = new Button("Add Flower");
    private Button filterButton = new Button("Filter Flower");
    private Button checkColorsButton = new Button("Search flower and display available colors");
    private Grid<FlowerDto> floareGrid = new Grid<>(FlowerDto.class);
    private ComboBox<ShopDto> shopComboBox = new ComboBox<>("Select a shop in witch to add the flower");
    private ComboBox<FlowerDto> flowerComboBox = new ComboBox<>("Select a flower ");
    private ComboBox<String> colorComboBox = new ComboBox<>("Select a color for the flower");
    private ComboBox<String> colorFilterComboBox = new ComboBox<>("Select a color for the filter");
    private ComboBox<String> disponibilityComboBox = new ComboBox<>();

    public FlowerFromShopGUI(FlowerFromShopPresenter presenter){
        this.presenter=presenter;
        presenter.init_IFlowerFromShopGUI(this);

        addButton.addClickListener(e -> presenter.addFlowerToShop());
        filterButton.addClickListener(e -> {
            System.out.println("Butonul de filtrare a fost apăsat!"); // Debugging
            presenter.filterFlowers();
        });

        setColorComboBox();
        setDisponibilityComboBox();
        flowerComboBox.setWidthFull();
        shopComboBox.setWidthFull();

        checkColorsButton = new Button("Check Available Colors", event -> {

            if (nameField != null) {
                List<String> colors = presenter.searchFlower();
                Notification.show("Available colors: " + String.join(", ", colors));
            } else {
                Notification.show("Please select a flower first!", 3000, Notification.Position.MIDDLE);
            }
        });

        floareGrid.addComponentColumn(flowerDto -> {
            Image image = new Image(flowerDto.imagePath(), "No Image");
            image.setWidth("100px");  // Setează dimensiunea imaginii
            image.setHeight("100px");
            return image;
        }).setHeader("Image");

        add(flowerComboBox,shopComboBox,colorComboBox,quantityField,addButton,
                nameField,checkColorsButton,colorFilterComboBox,disponibilityComboBox,filterButton,floareGrid);


    }
    private void setColorComboBox(){
        Collection<String> items = new ArrayList<>();
        items.add("RED");
        items.add("WHITE");
        items.add("PINK");
        items.add("YELLOW");
        colorComboBox.setItems(items);
        colorFilterComboBox.setItems(items);
    }
    private void setDisponibilityComboBox(){
        Collection<String> items = new ArrayList<>();
        items.add("DISPONIBIL");
        items.add("INDISPONIBIL");
        disponibilityComboBox.setItems(items);
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
        return colorComboBox.getValue();
    }

    @Override
    public String getColorFilter() {
        return colorFilterComboBox.getValue();
    }

    @Override
    public String getDisponibility() {
        return disponibilityComboBox.getValue();
    }

    @Override
    public int getQuantity() {
        return Integer.parseInt(quantityField.getValue());
    }
}
