package com.bankapplication.service;

import com.bankapplication.model.Account;
import com.bankapplication.model.Transaction;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bankapplication.model.TransactionType.CREDIT_TRANSFER;
import static com.bankapplication.model.TransactionType.DEBIT_TRANSFER;

@Service
public class TransactionService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void transfers(Account accountToDebit, Account accountToDeposit, Double amount) {
        try {
            accountToDebit.debit(amount);
            accountRepository.save(accountToDebit);
            Transaction transactionDebit = new Transaction(DEBIT_TRANSFER, amount, accountToDebit);
            transactionRepository.save(transactionDebit);


            accountToDeposit.deposit(amount);
            accountRepository.save(accountToDeposit);
            Transaction transactionCredit = new Transaction(CREDIT_TRANSFER, amount, accountToDeposit);
            transactionRepository.save(transactionCredit);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Some error happened during the transfer", e);
        }
    }
}
