package com.abbey2u.bankingsystem.service;

import com.abbey2u.bankingsystem.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    CustomerResponse createAccount(CustomerRequest customerRequest);
    CustomerResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest enquiryRequest);
    CustomerResponse creditAccount(CreditDebitRequest creditDebitRequest);
    CustomerResponse debitAccount(CreditDebitRequest creditDebitRequest);
    CustomerResponse transfer(TransferRequest transferRequest);
}
