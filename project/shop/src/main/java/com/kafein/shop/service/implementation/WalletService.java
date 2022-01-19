package com.kafein.shop.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kafein.shop.client.WalletServiceClient;
import com.kafein.shop.dto.Wallet;
import com.kafein.shop.service.ShopService;

@Service
public class WalletService implements ShopService {
	
	@Autowired
	WalletServiceClient walletServiceClient;
	
	public List<Wallet> getWalletsByUserId(Long userId) {
		return walletServiceClient.getWallets(userId).getBody();
	}
	
	public String withdrawsMoney(Long amount, Long walletId) {
		return walletServiceClient.withdrawsMoney(walletId, amount).getBody();
	}
}
