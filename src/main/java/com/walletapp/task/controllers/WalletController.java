package com.walletapp.task.controllers;

import com.walletapp.task.DTO.WalletCreationDto;
import com.walletapp.task.DTO.WalletRequest;
import com.walletapp.task.entities.Wallet;
import com.walletapp.task.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<?> createWallet(@RequestBody WalletCreationDto walletCreationDto) {
        try {
            walletService.createNewWallet(walletCreationDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/wallet")
    public ResponseEntity<?> handleTransaction(@RequestBody WalletRequest request) {
        try {
            walletService.processTransaction(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/wallets/{id}")
    public ResponseEntity<Wallet> getBalance(@PathVariable Long id) {
        return walletService.getWalletById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}