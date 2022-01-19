package com.kafein.shop.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kafein.shop.dto.Wallet;

@FeignClient(name = "wallet",url = "http://localhost:8005")
public interface WalletServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/wallet/{userId}", consumes = "application/json")
	ResponseEntity<List<Wallet>> getWallets(@PathVariable("userId") Long userId);
	
	@RequestMapping(method = RequestMethod.POST, value = "/wallet/withdrawsmoney/{walletId}/{amount}", consumes = "application/json")
	ResponseEntity<String> withdrawsMoney(@PathVariable("walletId") Long walletId, @PathVariable("amount") Long amount);
}
