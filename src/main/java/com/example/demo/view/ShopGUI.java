package com.example.demo.view;

import com.example.demo.presenter.InterfaceGUI.IShopGUI;
import com.example.demo.presenter.ShopPresenter;
import com.example.demo.presenter.dto.ShopDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.List;

@PageTitle("Flower Shops")
@Route(value = "/shops")
@Component
@UIScope
public class ShopGUI extends VerticalLayout implements IShopGUI {
    private ShopPresenter presenter;
    private TextField nameField = new TextField("Name");
    private TextField addressField = new TextField("Address");
    private Button addButton = new Button("Add Shop");
    private Button deleteButton = new Button("Delete Shop");
    private Button updateButton = new Button("Update Shop");
    private Grid<ShopDto> shopGrid = new Grid<>(ShopDto.class);
    private ComboBox<ShopDto> shopComboBox = new ComboBox<>("Select a shop for delete or update");
    public ShopGUI(ShopPresenter presenter){
        this.presenter=presenter;
        presenter.init_IShopGUI(this);
        addButton.addClickListener(e -> presenter.addShop());
        deleteButton.addClickListener(e -> presenter.deleteShop());
        updateButton.addClickListener(e -> presenter.updateShop());

        shopComboBox.setWidthFull();
        add(new Button("Reimprospatare", e -> presenter.showAllShops()));
        add(nameField,addressField,addButton,shopComboBox,deleteButton,updateButton,shopGrid);

    }
    @Override
    public void setGrid(List<ShopDto> shops) {
        shopGrid.setItems(shops);
    }

    @Override
    public void setComboBox(List<ShopDto> shops) {
        shopComboBox.setItems(shops);
    }

    @Override
    public ShopDto getSelectedShop() {
        return shopComboBox.getValue();
    }


    @Override
    public String getName() {
        return nameField.getValue();
    }

    @Override
    public String getAddress() {
        return addressField.getValue();
    }
}
