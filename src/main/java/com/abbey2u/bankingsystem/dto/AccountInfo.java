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
public class AccountInfo {

    @Schema(name = "Customer Account Name")
    private String accountName;

    @Schema(name = "Customer Account Balance")
    private BigDecimal accountBalance;

    @Schema(name = "Customer Account Number")
    private String accountNumber;

}
