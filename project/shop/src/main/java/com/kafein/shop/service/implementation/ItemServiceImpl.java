package com.kafein.shop.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kafein.shop.entity.Item;
import com.kafein.shop.repository.ItemRepository;
import com.kafein.shop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    public List<Item> getItemsByCartId(Long cartId){

    	return itemRepository.findByShoppingCart_Id(cartId);
    }
}
