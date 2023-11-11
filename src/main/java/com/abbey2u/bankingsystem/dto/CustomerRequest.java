package com.abbey2u.bankingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @Schema(name = "Customer First Name")
    private String firstName;

    @Schema(name = "Customer Last Name")
    private String lastName;

    @Schema(name = "Customer Other Name")
    private String otherName;

    @Schema(name = "Customer gender")
    private String gender;

    @Schema(name = "Customer address")
    private String address;

    @Schema(name = "Customer State of Origin")
    private String stateOfOrigin;

    @Schema(name = "Customer email")
    private String email;

    @Schema(name = "Customer password")
    private String password;

    @Schema(name = "Customer Phone Number")
    private String phoneNumber;

    @Schema(name = "Customer Alternative Phone Number")
    private String alternativePhoneNumber;
}
