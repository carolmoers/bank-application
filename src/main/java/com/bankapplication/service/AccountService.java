package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByNumber(Integer number) {
        return accountRepository.findByNumber(number);
    }
}
