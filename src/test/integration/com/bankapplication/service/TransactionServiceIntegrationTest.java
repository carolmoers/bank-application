package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionServiceIntegrationTest {
    private Account accountToDebitBalance;
    private Account accountToIncreaseBalance;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        accountToDebitBalance = new Account(890, Double.valueOf(150));
        accountRepository.save(accountToDebitBalance);
        accountToIncreaseBalance = new Account(456, Double.valueOf(50));
        accountRepository.save(accountToIncreaseBalance);
    }

    @After
    public void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void shouldUpdatesAccountBalanceCorrectly() {
        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(Double.valueOf(100), accountToDebitBalance.getBalance());
        assertEquals(Double.valueOf(100), accountToIncreaseBalance.getBalance());
    }

    @Test
    public void shouldCreateTransactionsCorrectly() {
        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(2, transactionRepository.findAll().size());
    }
}
