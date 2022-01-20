package com.kafein.shop.service.implementation;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.kafein.shop.dto.Wallet;
import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;
import com.kafein.shop.repository.ShoppingCartRepository;
import com.kafein.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Autowired
    ItemServiceImpl itemServiceImpl;

    @Autowired
    WalletService walletService;

    @Override
    public ShoppingCart create(ShoppingCart newCart) {
        if (newCart.getId() != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
        return shoppingCartRepository.saveAndFlush(newCart);
    }

    @Override
    public ShoppingCart addToCart(Long id, List<Item> newItems) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);
        for (Item news : newItems) {
            news.setCart(new ShoppingCart(id));
        }
        shoppingCart.getItems().addAll(newItems);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeFromCart(Long id, List<Item> newItems) {
        ShoppingCart query = shoppingCartRepository.findShoppingCartById(id);
        newItems.forEach(query.getItems()::remove);
        return shoppingCartRepository.save(query);
    }

    @Override
    public ShoppingCart getCart(Long id) {
        return shoppingCartRepository.findShoppingCartById(id);
    }

    public void pay(Long userId, Long cartId) throws Exception {
    	List<Wallet> wallets = walletService.getWalletsByUserId(userId).stream().
    			sorted((o1, o2)->o2.getAmount().compareTo(o1.getAmount())).collect(Collectors.toList());
        //List<Wallet> wallets = walletService.getWalletsByUserId(userId);

        Long totalWalletAmount = calculateWalletTotalAmount(wallets);
        Long totalSpendAmount = calculateTotalAmount(cartId);

        if (totalSpendAmount > totalWalletAmount) {
            throw new Exception();
        }

        Long spendAmount = (long) 0;
        int i = 0;
        while (totalSpendAmount > 0 && i < wallets.size()) {
            Wallet wallet = wallets.get(i);
            spendAmount = totalSpendAmount > wallet.getAmount().longValue() ? wallet.getAmount().longValue() : totalSpendAmount;
            walletService.withdrawsMoney(spendAmount, wallet.getId());
            totalSpendAmount -= spendAmount;
            i++;
        }
    }

    private Long calculateWalletTotalAmount(List<Wallet> wallets) {
        Long totalAmount = (long) 0;
        for (Wallet wallet : wallets) {
            totalAmount += wallet.getAmount().longValue();
        }
        return totalAmount;
    }

    private Long calculateTotalAmount(Long cartId) throws Exception {
        Long totalAmount = (long) 0;
        List<Item> items = itemServiceImpl.getItemsByCartId(cartId);
        if(Objects.isNull(items)){
            throw new Exception();
        }

        for (Item item : items) {
            totalAmount += item.getPrice().longValue();
        }
        return totalAmount;
    }
}
