package com.kafein.shop.repository;

import com.kafein.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemById(Long id);
    List<Item> findByShoppingCart_Id(Long id);
    
    //TODO findItemByShoppingCart implement
    
}
