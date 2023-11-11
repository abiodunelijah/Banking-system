package com.abbey2u.bankingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    @Schema(name = "Customer Response code")
    private String responseCode;

    @Schema(name = "Customer Response Message")
    private String responseMessage;

    @Schema(name = "Customer Account Information")
    private AccountInfo accountInfo;
}
