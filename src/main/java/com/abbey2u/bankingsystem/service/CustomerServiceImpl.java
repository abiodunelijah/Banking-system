package com.abbey2u.bankingsystem.service;

import com.abbey2u.bankingsystem.config.JwtTokenProvider;
import com.abbey2u.bankingsystem.dto.*;
import com.abbey2u.bankingsystem.entity.Customer;
import com.abbey2u.bankingsystem.entity.Role;
import com.abbey2u.bankingsystem.repository.CustomerRepository;
import com.abbey2u.bankingsystem.utils.AccountUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailServiceImpl emailService;
    private final TransactionServiceImpl transactionService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerServiceImpl(CustomerRepository customerRepository, EmailServiceImpl emailService, TransactionServiceImpl transactionService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
        this.transactionService = transactionService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public CustomerResponse createAccount(CustomerRequest customerRequest) {
        /**
         * Create an account - saving a new customer into the database
         * check if customer already has an account
         */

        if (customerRepository.existsCustomerByEmail(customerRequest.getEmail())){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer newCustomer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .otherName(customerRequest.getOtherName())
                .email(customerRequest.getEmail())
                .password(passwordEncoder.encode(customerRequest.getPassword()))
                .address(customerRequest.getAddress())
                .gender(customerRequest.getGender())
                .stateOfOrigin(customerRequest.getStateOfOrigin())
                .phoneNumber(customerRequest.getPhoneNumber())
                .alternativePhoneNumber(customerRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_ADMIN"))
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)

                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);

        return CustomerResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedCustomer.getAccountBalance())
                        .accountNumber(savedCustomer.getAccountNumber())
                        .accountName(savedCustomer.getFirstName() +" "+
                                savedCustomer.getLastName()+ " " +
                                savedCustomer.getOtherName())
                        .build())
                .build();
    }

    public CustomerResponse login(LoginDTO loginDTO){
        Authentication authentication = null;
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        EmailDetails loginAlert = EmailDetails.builder()
                .subject("You are logged in!")
                .recipient(loginDTO.getEmail())
                .messageBody("You are logged in into your account. If you did not initiate this request, please contact your bank")
                .build();

        emailService.sendEmailAlert(loginAlert);

        return CustomerResponse.builder()
                .responseCode("Login success")
                .responseMessage(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    public CustomerResponse balanceEnquiry(EnquiryRequest enquiryRequest) {

        Boolean isAccountExist = customerRepository.existsCustomerByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isAccountExist){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

            Customer foundCustomer = customerRepository.findCustomerByAccountNumber(enquiryRequest.getAccountNumber());

            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(foundCustomer.getAccountBalance())
                            .accountNumber(foundCustomer.getAccountNumber())
                            .accountName(foundCustomer.getFirstName() +" "+ foundCustomer.getLastName() +" "+ foundCustomer.getOtherName())
                            .build())
                    .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist = customerRepository.existsCustomerByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isAccountExist){
         return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        Customer foundCustomer = customerRepository.findCustomerByAccountNumber(enquiryRequest.getAccountNumber());
        return foundCustomer.getFirstName() +" "+ foundCustomer.getLastName()+" "+ foundCustomer.getOtherName();
    }

    @Override
    public CustomerResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        Boolean isAccountExist = customerRepository.existsCustomerByAccountNumber(creditDebitRequest.getAccountNumber());

        if (!isAccountExist){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Customer customerToCredit = customerRepository.findCustomerByAccountNumber(creditDebitRequest.getAccountNumber());
        customerToCredit.setAccountBalance(customerToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
        customerRepository.save(customerToCredit);

       TransactionDTO transactionDTO = TransactionDTO.builder()
               .accountNumber(customerToCredit.getAccountNumber())
               .transactionType("CREDIT")
               .amount(creditDebitRequest.getAmount())
               .build();
       transactionService.saveTransaction(transactionDTO);

        return CustomerResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(customerToCredit.getFirstName()+ " "+
                                customerToCredit.getLastName()+" "+
                                customerToCredit.getOtherName())
                        .accountBalance(customerToCredit.getAccountBalance())
                        .accountNumber(customerToCredit.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public CustomerResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        //check if account exist
        //check if amount you intend to withdraw is not more than the current account balance

        Boolean isAccountExist = customerRepository.existsCustomerByAccountNumber(creditDebitRequest.getAccountNumber());

        if (!isAccountExist){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer customerToDebit = customerRepository.findCustomerByAccountNumber(creditDebitRequest.getAccountNumber());
        BigInteger availableBalance = customerToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount =creditDebitRequest.getAmount().toBigInteger();

        if (availableBalance.intValue() < debitAmount.intValue()){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        else {
            customerToDebit.setAccountBalance(customerToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            customerRepository.save(customerToDebit);

            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .accountNumber(customerToDebit.getAccountNumber())
                    .transactionType("CREDIT")
                    .amount(creditDebitRequest.getAmount())
                    .build();
            transactionService.saveTransaction(transactionDTO);

            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(creditDebitRequest.getAccountNumber())
                            .accountName(customerToDebit.getFirstName() + " " +
                                    customerToDebit.getLastName() +" "+
                                    customerToDebit.getOtherName())
                            .accountBalance(customerToDebit.getAccountBalance())
                            .build())
                    .build();
        }
    }

    @Override
    public CustomerResponse transfer(TransferRequest transferRequest) {
        //get the account to debit check if it exists
        //check if amount you intend to transfer is not more than the current account balance
        // debit the account
        // get the account to credit
        // credit the account
        boolean destinationAccountExist = customerRepository.existsCustomerByAccountNumber(transferRequest.getDestinationAccountNumber());
        if (!destinationAccountExist){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer sourceAccountCustomer = customerRepository.findCustomerByAccountNumber(transferRequest.getSourceAccountNumber());
        String sourceUsername = sourceAccountCustomer.getFirstName() + " " + sourceAccountCustomer.getLastName()+ " "+ sourceAccountCustomer.getOtherName();
        if (transferRequest.getAmount().compareTo(sourceAccountCustomer.getAccountBalance()) > 0){
            return CustomerResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        sourceAccountCustomer.setAccountBalance(sourceAccountCustomer.getAccountBalance().subtract(transferRequest.getAmount()));
        customerRepository.save(sourceAccountCustomer);

        EmailDetails debitAlert = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountCustomer.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been deducted from your account. Your current balance is " + sourceAccountCustomer.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);

        Customer destinationAccountCustomer = customerRepository.findCustomerByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccountCustomer.setAccountBalance(destinationAccountCustomer.getAccountBalance().add(transferRequest.getAmount()));
        //String recipientUsername = destinationAccountCustomer.getFirstName() + " " + destinationAccountCustomer.getLastName() + " " + destinationAccountCustomer.getOtherName();
        customerRepository.save(destinationAccountCustomer);

        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(sourceAccountCustomer.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been sent to your account from " + sourceUsername + " Your current balance is: " + destinationAccountCustomer.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .accountNumber(destinationAccountCustomer.getAccountNumber())
                .transactionType("CREDIT")
                .amount(transferRequest.getAmount())
                .build();
        transactionService.saveTransaction(transactionDTO);

        return CustomerResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }
}
