package com.abbey2u.bankingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDTO {

    @Schema(name = "Transaction Type")
    private String transactionType;

    @Schema(name = "Transaction Amount")
    private BigDecimal amount;

    @Schema(name = "Transaction Account Number")
    private String accountNumber;

    @Schema(name = "Transaction Status")
    private String status;
}
