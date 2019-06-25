package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import org.junit.Test;
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

    private static final boolean IS_SUCCESSFUL = true;
    private static final boolean NOT_SUCCESSFUL = false;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    public void transferAmountBetweenAccounts() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(150));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        boolean transactionResult =
                transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(transactionResult, IS_SUCCESSFUL);
        assertEquals(Double.valueOf(100), accountToDebitBalance.getBalance());
        assertEquals(Double.valueOf(100), accountToIncreaseBalance.getBalance());
        verify(accountRepository, times(2)).save(any());
        verify(transactionRepository, times(2)).save(any());
    }

    @Test
    public void doesNotTransferAmountBetweenAccountsWhenItIsLessThanBalanceToDebit() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(5));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        boolean transactionResult =
                transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(50));

        assertEquals(transactionResult, NOT_SUCCESSFUL);
        assertEquals(Double.valueOf(5), accountToDebitBalance.getBalance());
        assertEquals(Double.valueOf(50), accountToIncreaseBalance.getBalance());
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrownExceptionIfAnyErrorHappenWhenTransfering() {
        Account accountToDebitBalance = new Account(890, Double.valueOf(50));
        Account accountToIncreaseBalance = new Account(456, Double.valueOf(50));

        when(transactionRepository.save(any()))
                .thenThrow(DataIntegrityViolationException.class);

        transactionService.transfers(accountToDebitBalance, accountToIncreaseBalance, Double.valueOf(20));

    }
}