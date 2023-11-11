package com.abbey2u.bankingsystem.utils;

import java.time.Year;

import static java.lang.Math.*;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MESSAGE="This customer already has an account created!";
    public static final String ACCOUNT_CREATION_SUCCESS="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Account has been successfully created ";
    public static final String ACCOUNT_NOT_EXIST_CODE="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE="Customer with the provide Account number does not exist";
    public static final String ACCOUNT_FOUND_CODE="004";
    public static final String ACCOUNT_FOUND_SUCCESS="Customer found Found";
    public static final String ACCOUNT_CREDITED_SUCCESS="005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE="This Account has been credited successfully!";

    public static final String INSUFFICIENT_BALANCE_CODE="006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE="Insufficient Balance!";
    public static final String ACCOUNT_DEBITED_SUCCESS="007";
    public static final String ACCOUNT_DEBITED_MESSAGE="Account has been debited successfully";
    public static final String TRANSFER_SUCCESSFUL_CODE="008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE="Transfer successful!";

    public static String generateAccountNumber(){
        /**
         * 2022 + randomSizeDigits
         */
        Year curentYear = Year.now();
        int min = 100_000;
        int max = 999_999;
        //a random number btw min and max
        int randomNumber =(int) floor(random() * (max - min + 1) + min);

        //convert the currentYear and randomNumber to strings and then concatenate
        String year = String.valueOf(curentYear);
        String rand = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(rand).toString();
    }

}
