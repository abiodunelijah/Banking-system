package com.abbey2u.bankingsystem.repository;

import com.abbey2u.bankingsystem.dto.CustomerRequest;
import com.abbey2u.bankingsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsCustomerByEmail(String email);

    Optional<Customer> findCustomerByEmail(String email);

    Boolean existsCustomerByAccountNumber(String accountNumber);
    Customer findCustomerByAccountNumber(String accountNumber);
}
