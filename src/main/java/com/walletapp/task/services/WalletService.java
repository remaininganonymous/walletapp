package com.walletapp.task.services;

import com.walletapp.task.DTO.WalletRequest;
import com.walletapp.task.entities.Wallet;
import com.walletapp.task.entities.enums.OperationType;
import com.walletapp.task.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void processTransaction(WalletRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Кошелек не найден"));

        if ("DEPOSIT".equalsIgnoreCase(request.getOperationType())) {
            wallet.setBalance(wallet.getBalance() + request.getAmount());
        } else if ("WITHDRAW".equalsIgnoreCase(request.getOperationType())) {
            if (wallet.getBalance() < request.getAmount()) {
                throw new IllegalArgumentException("Недостаточно средств");
            }
            wallet.setBalance(wallet.getBalance() - request.getAmount());
        } else {
            throw new IllegalArgumentException("Несуществующий тип операции");
        }

        walletRepository.save(wallet);
    }

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }
}
