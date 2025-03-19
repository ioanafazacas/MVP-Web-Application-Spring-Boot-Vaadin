package com.example.demo.model.repository;

import com.example.demo.model.FlowerFromFlowerShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlowerFromShopRepository extends JpaRepository<FlowerFromFlowerShop, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM FlowerFromFlowerShop f WHERE f.flower.id = :flowerId")
    void deleteByFlowerId(int flowerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FlowerFromFlowerShop f WHERE f.flowerShop.id = :shopId")
    void deleteByShopId(int shopId);

    // 🔹 Returnează toate florile cu o anumită culoare
    @Query("SELECT f FROM FlowerFromFlowerShop f WHERE f.color = :color")
    List<FlowerFromFlowerShop> findByColor(@Param("color") String color);

    // 🔹 Returnează toate florile care au quantity = 0 (epuizate)
    @Query("SELECT f FROM FlowerFromFlowerShop f WHERE f.quantity = 0")
    List<FlowerFromFlowerShop> findOutOfStockFlowers();

    // 🔹 Returnează toate florile care au quantity > 0 (în stoc)
    @Query("SELECT f FROM FlowerFromFlowerShop f WHERE f.quantity > 0")
    List<FlowerFromFlowerShop> findAvailableFlowers();
}
