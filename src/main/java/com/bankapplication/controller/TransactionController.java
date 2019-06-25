package com.bankapplication.controller;

import com.bankapplication.model.Account;
import com.bankapplication.service.AccountService;
import com.bankapplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/")
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferInformation transferInformation) {


        Account accountToDebit = accountService.findByNumber(transferInformation.getAccountToDebitNumber());
        Account accountToDeposit = accountService.findByNumber(transferInformation.getAccountToDepositNumber());

        transactionService.transfers(accountToDebit, accountToDeposit, transferInformation.getAmount());

        return ResponseEntity.ok(
                format("Transfer from account [%s] to account [%s] completed with success",
                        transferInformation.getAccountToDebitNumber(),
                        transferInformation.getAccountToDepositNumber()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentNotNullError(){

        return ResponseEntity
                .status(BAD_REQUEST)
                .body("Transfer information with null value");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalStateError(IllegalArgumentException e){

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(format("Transfer failed: %s", e.getMessage()));
    }

}
