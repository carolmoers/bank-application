package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private static final int NUMBER = 890;
    private static final Double BALANCE = Double.valueOf(150);

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void shouldFinAccountByNumber() {
        Account account = new Account(NUMBER, BALANCE);

        when(accountRepository.findByNumber(890)).thenReturn(account);

        Account accountByNumber = accountService.findByNumber(NUMBER);
        assertEquals(accountByNumber, account);
        verify(accountRepository, times(1)).findByNumber(NUMBER);
    }
}