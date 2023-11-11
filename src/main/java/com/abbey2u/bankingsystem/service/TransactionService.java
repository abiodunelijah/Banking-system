package com.abbey2u.bankingsystem.service;

import com.abbey2u.bankingsystem.dto.TransactionDTO;
import com.abbey2u.bankingsystem.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDTO transactionDTO);
}
