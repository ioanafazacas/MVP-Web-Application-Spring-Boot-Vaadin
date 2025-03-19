package com.example.demo.model.repository;


import com.example.demo.model.FlowerShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FlowerShopRepository extends JpaRepository<FlowerShop, Integer> {
    @Query("SELECT s FROM FlowerShop s WHERE s.name = :name and s.address = :address")
    FlowerShop findByNameAndAddress(String name,String address);

    @Modifying
    @Transactional
    @Query("UPDATE FlowerShop s SET s.address = :address WHERE s.name = :name")
    void update(@Param("name") String name,
                @Param("address") String address);
}
