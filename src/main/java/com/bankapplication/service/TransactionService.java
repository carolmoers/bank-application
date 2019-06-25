package com.bankapplication.service;

import com.bankapplication.model.Account;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public boolean transfers(Account accountToDebit, Account accountToDeposit, Double amount) {
        if(accountToDebit.debit(amount)) {
            return accountToDeposit.deposit(amount);

        }

        return false;
    }
}
