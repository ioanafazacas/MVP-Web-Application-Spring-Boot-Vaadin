package com.example.demo.view;

import com.example.demo.presenter.InterfaceGUI.IShopGUI;
import com.example.demo.presenter.ShopPresenter;
import com.example.demo.presenter.dto.ShopDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
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
    Button exportCsvButton = new Button("Export CSV");
    Button exportDocButton = new Button("Export DOC");
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

        exportCsvButton.addClickListener(e -> {
            ShopDto selectedShop = shopComboBox.getValue();
            if (selectedShop != null) {
                try {
                    Path csvPath = presenter.exportOutOfStockFlowersToCSV(selectedShop.name(), selectedShop.address());
                    Anchor downloadLink = new Anchor(csvPath.toUri().toString(), "Download CSV");
                    downloadLink.getElement().setAttribute("download", true);
                    add(downloadLink);
                } catch (IOException ex) {
                    Notification.show("Error generating CSV file!");
                }
            }
        });

        exportDocButton.addClickListener(e -> {
            ShopDto selectedShop = shopComboBox.getValue();
            if (selectedShop != null) {
                try {
                    Path docPath = presenter.exportOutOfStockFlowersToDoc(selectedShop.name(), selectedShop.address());
                    Anchor downloadLink = new Anchor(docPath.toUri().toString(), "Download DOC");
                    downloadLink.getElement().setAttribute("download", true);
                    add(downloadLink);
                } catch (IOException ex) {
                    Notification.show("Error generating DOC file!");
                }
            }
        });

        add(exportCsvButton, exportDocButton);

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
