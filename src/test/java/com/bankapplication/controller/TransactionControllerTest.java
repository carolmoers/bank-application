package com.bankapplication.controller;

import com.bankapplication.model.Account;
import com.bankapplication.service.AccountService;
import com.bankapplication.service.TransactionService;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    private static final int ACCOUNT_TO_DEBIT_NUMBER = 890;
    private static final int ACCOUNT_TO_DEPOSIT_NUMBER = 456;

    @Mock
    private TransactionService transactionService;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void shouldReturnSuccessWhenTransferIsSuccessful() throws NotFoundException {
        Account accountToDebit = new Account(ACCOUNT_TO_DEBIT_NUMBER, Double.valueOf(150));
        Account accountToDeposit = new Account(ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(50));

        doReturn(accountToDebit).when(accountService).findByNumber(ACCOUNT_TO_DEBIT_NUMBER);
        doReturn(accountToDeposit).when(accountService).findByNumber(ACCOUNT_TO_DEPOSIT_NUMBER);
        doNothing().when(transactionService).transfers(any(), any(), any());

        TransferInformation transferInformation =
                new TransferInformation(ACCOUNT_TO_DEBIT_NUMBER, ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(50));

        ResponseEntity<String> response = transactionController.transfer(transferInformation);


        assertEquals(response.getStatusCodeValue(), 200);
        assertEquals(response.getBody(), "Transfer from account [890] to account [456] completed with success");
    }
}