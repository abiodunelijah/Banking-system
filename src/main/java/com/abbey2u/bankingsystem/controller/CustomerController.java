package com.abbey2u.bankingsystem.controller;

import com.abbey2u.bankingsystem.dto.*;
import com.abbey2u.bankingsystem.service.CustomerService;
import com.abbey2u.bankingsystem.service.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = " Account Management APIs for Customer")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerServiceImpl = customerService;
    }

    @Operation(
            summary = "Create Customer Account",
            description = "This endpoint creates a new account with ID"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public CustomerResponse createAccount(@RequestBody CustomerRequest customerRequest){
        return customerServiceImpl.createAccount(customerRequest);
    }

    @PostMapping("/login")
    public CustomerResponse login(@RequestBody LoginDTO loginDTO){
        return customerServiceImpl.login(loginDTO);
    }

    @Operation(
            summary = "Balance Enquiry",
            description = "This endpoint checks the account balance"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/balanceEnquiry")
    public CustomerResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return customerServiceImpl.balanceEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "Name Enquiry",
            description = "This endpoint checks the name on the account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return customerServiceImpl.nameEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "Credit Account",
            description = "This endpoint credit account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/credit")
    public CustomerResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return customerServiceImpl.creditAccount(creditDebitRequest);
    }

    @Operation(
            summary = "Debit Account",
            description = "This endpoint debit account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/debit")
    public CustomerResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return customerServiceImpl.debitAccount(creditDebitRequest);
    }

    @Operation(
            summary = "Transfer",
            description = "This endpoint debit from the source account and transfer to destination account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping("/transfer")
    public CustomerResponse transfer(@RequestBody TransferRequest transferRequest){
        return customerServiceImpl.transfer(transferRequest);
    }
}
