package com.walletapp.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.task.DTO.WalletCreationDto;
import com.walletapp.task.DTO.WalletRequest;
import com.walletapp.task.entities.Wallet;
import com.walletapp.task.services.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WalletService walletService;

    @Test
    public void testCreateWallet() throws Exception {
        WalletCreationDto walletCreationDto = new WalletCreationDto();
        walletCreationDto.setInitialDeposit(Float.valueOf(1000));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletCreationDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testHandleTransaction() throws Exception {
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setWalletId(1L);
        walletRequest.setOperationType("DEPOSIT");
        walletRequest.setAmount(Long.valueOf(1000));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(walletRequest)))
                .andExpect(status().isOk());
    }

    /*@Test
    public void testGetBalance() throws Exception {
        //wait a sec
    }*/

    @Test
    public void testGetBalance_NotFound() throws Exception {
        Mockito.when(walletService.getWalletById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/1"))
                .andExpect(status().isNotFound());
    }
}
