package com.abbey2u.bankingsystem.repository;

import com.abbey2u.bankingsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
