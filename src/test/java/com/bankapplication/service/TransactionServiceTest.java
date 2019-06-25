package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldTransferAmountBetweenAccounts() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(150));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(Double.valueOf(100), accountToDebitBalance.getBalance());
        assertEquals(Double.valueOf(100), accountToIncreaseBalance.getBalance());
        verify(accountRepository, times(2)).save(any());
        verify(transactionRepository, times(2)).save(any());
    }

    @Test
    public void shouldNotTransferAmountBetweenAccountsWhenItIsLessThanBalanceToDebit() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(5));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Some error happened during the transfer");

        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(Double.valueOf(5), accountToDebitBalance.getBalance());
        assertEquals(Double.valueOf(50), accountToIncreaseBalance.getBalance());
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrownExceptionIfAnyErrorHappenDuringTransfer() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(50));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        when(transactionRepository.save(any()))
                .thenThrow(IllegalArgumentException.class);

        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(20));
    }
}