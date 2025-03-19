package com.example.demo.model.repository;

import com.example.demo.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FlowerRepository extends JpaRepository<Flower, Integer> {
    @Query("SELECT f FROM Flower f WHERE f.name = :name ")
    Flower findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Flower f SET f.price = :price, f.imagePath = :imagePath WHERE f.name = :name")
    void update(@Param("name") String name,
                @Param("price") float price,
                @Param("imagePath") String imagePath);
}
