package com.kafein.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.kafein.shop.dto.PayRequestDto;
import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;
import com.kafein.shop.service.implementation.ShoppingCartServiceImpl;
import com.kafein.shop.service.implementation.WalletService;

@RestController
@RequestMapping("/shop/cart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartServiceImpl shoppingCartService;
    
    @Autowired
    WalletService walletService;

    @PostMapping("/new")
    public ResponseEntity<ShoppingCart> create(@RequestBody ShoppingCart shoppingCart) {
        try {
            return new ResponseEntity<>(shoppingCartService.create(shoppingCart), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<ShoppingCart> addItemsToCart(@PathVariable Long id, @RequestBody List<Item> itemList) {
        try {
            return new ResponseEntity<>(shoppingCartService.addToCart(id, itemList), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/remove")
    public ResponseEntity<ShoppingCart> removeItemsFromCart(@PathVariable Long id, @RequestBody List<Item> itemList) {
        try {
            return new ResponseEntity<>(shoppingCartService.removeFromCart(id, itemList), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(shoppingCartService.getCart(id), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody PayRequestDto payRequestDto) {
        try {
            shoppingCartService.pay(payRequestDto.getUserId(),payRequestDto.getCartId());
            return new ResponseEntity<>("Success",HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //TODO implement a controller and required services to pay for the shopping card through the given wallet

}
