package com.bankapplication.service;

import com.bankapplication.model.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    public static final boolean IS_SUCCESSFUL = true;

    TransactionService transactionService;

    @Before
    public void before() {
        transactionService = new TransactionService();
    }

    @Test
    public void transferAmountBetweenAccounts() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(150));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        boolean transactionResult =
                transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(transactionResult, IS_SUCCESSFUL);
        assertEquals(Double.valueOf(100), accountToIncreaseBalance.getBalance());
        assertEquals(Double.valueOf(100), accountToDebitBalance.getBalance());
    }
}