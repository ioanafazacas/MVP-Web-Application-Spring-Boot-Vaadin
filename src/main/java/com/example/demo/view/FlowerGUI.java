package com.example.demo.view;

import com.example.demo.model.Flower;
import com.example.demo.presenter.FlowerGUIInterface;
import com.example.demo.presenter.FlowerPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("flower")
public class FlowerGUI extends VerticalLayout implements FlowerGUIInterface {
    private Grid<Flower> floareGrid = new Grid<>(Flower.class);
    private FlowerPresenter presenter;
    public FlowerGUI(){
        presenter = new FlowerPresenter(this);
        add(floareGrid);
        add(new Button("Reimprospatare", e -> presenter.showAllFlowers()));
    }

    @Override
    public void showAllFlowers(List<Flower> flowers) {
        floareGrid.setItems(flowers);
    }

    @Override
    public void showFlower(Flower flower) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public float getPrice() {
        return 0;
    }

    @Override
    public String getImagePath() {
        return null;
    }
}

