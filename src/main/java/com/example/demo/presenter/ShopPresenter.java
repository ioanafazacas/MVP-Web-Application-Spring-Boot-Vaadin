package com.example.demo.presenter;

import com.example.demo.model.Flower;
import com.example.demo.model.FlowerFromFlowerShop;
import com.example.demo.model.FlowerShop;
import com.example.demo.model.repository.FlowerFromShopRepository;
import com.example.demo.model.repository.FlowerShopRepository;
import com.example.demo.presenter.InterfaceGUI.IShopGUI;
import com.example.demo.presenter.dto.FlowerDto;
import com.example.demo.presenter.dto.ShopDto;
import com.example.demo.presenter.dto.mapper.FlowerMapper;
import com.example.demo.presenter.dto.mapper.ShopMapper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    private List<FlowerDto> generateOutOfStocList() {
        ShopDto shopDto = view.getSelectedShop();
        FlowerShop shop = repository.findByNameAndAddress(shopDto.name(), shopDto.address());
        List<FlowerFromFlowerShop> flowers = flowerFromShopRepository.findOutOfStockFlowersByShop(shop);
        return FlowerMapper.flowerListEntityToDto(flowers.stream().map(FlowerFromFlowerShop::getFlower).toList());
    }

    public Path exportOutOfStockFlowersToCSV(String shopName, String shopAddress) throws IOException {
        List<FlowerDto> flowers = generateOutOfStocList();
        Path csvFile = Files.createTempFile("OutOfStockFlowers", ".csv");

        try (BufferedWriter writer = Files.newBufferedWriter(csvFile, StandardOpenOption.WRITE)) {
            writer.write("Name,Color,Price\n"); // Antetul CSV
            for (FlowerDto flower : flowers) {
                writer.write(String.format("%s,%s,%.2f\n", flower.name(), flower.price()));
            }
        }

        return csvFile;
    }
    public Path exportOutOfStockFlowersToDoc(String shopName, String shopAddress) throws IOException {
        List<FlowerDto> flowers = generateOutOfStocList();
        Path docFile = Files.createTempFile("OutOfStockFlowers", ".docx");

        try (FileOutputStream out = new FileOutputStream(docFile.toFile())) {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setText("Out of Stock Flowers\n\n");

            for (FlowerDto flower : flowers) {
                XWPFParagraph p = document.createParagraph();
                XWPFRun r = p.createRun();
                r.setText("Name: " + flower.name() + ", Price: " + flower.price());
            }

            document.write(out);
            document.close();
        }

        return docFile;
    }
}
