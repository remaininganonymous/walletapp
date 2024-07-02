package com.walletapp.task.DTO;

import com.walletapp.task.entities.enums.OperationType;
import lombok.Getter;
import lombok.Setter;

public class WalletRequest {
    @Getter
    @Setter
    private Long walletId;

    @Getter
    @Setter
    private String operationType;

    @Getter
    @Setter
    private Long amount;
}
