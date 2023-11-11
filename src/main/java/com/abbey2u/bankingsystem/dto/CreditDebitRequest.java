package com.abbey2u.bankingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebitRequest {
    @Schema(name = "Customer Number")
    private String accountNumber;

    @Schema(name = "Customer Amount on debit or credit")
    private BigDecimal amount;


}
