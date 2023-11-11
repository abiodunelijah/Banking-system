package com.abbey2u.bankingsystem.controller;

import com.abbey2u.bankingsystem.entity.Transaction;
import com.abbey2u.bankingsystem.service.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bankStatement")
public class TransactionController {

    private final BankStatement bankStatement;

    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate){
        return generateBankStatement(accountNumber, startDate, endDate);
    }
}
