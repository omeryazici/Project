package com.kafein.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.kafein.wallet.entity.Wallet;
import com.kafein.wallet.service.implementation.WalletServiceImpl;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletServiceImpl walletServiceImpl;
    
    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<List<Wallet>> getWallets(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(walletServiceImpl.getWalletsByUserId(userId), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/withdrawsmoney/{amount}/{walletId}")
    @ResponseBody
    public ResponseEntity<String> getWallets( @PathVariable Long amount,@PathVariable Long walletId) {
        try {
        	walletServiceImpl.withdrawsMoney(amount, walletId);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>("Insufficient balance",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
