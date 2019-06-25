package com.bankapplication.controller;

import javax.validation.constraints.NotNull;

public class TransferInformation {
    @NotNull
    private Integer accountToDebitNumber;
    @NotNull
    private Integer accountToDepositNumber;
    @NotNull
    private Double amount;

    public TransferInformation() {
    }

    public TransferInformation(Integer accountToDebitNumber, Integer accountToDepositNumber, Double amount) {
        this.accountToDebitNumber = accountToDebitNumber;
        this.accountToDepositNumber = accountToDepositNumber;
        this.amount = amount;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Integer getAccountToDebitNumber() {
        return this.accountToDebitNumber;
    }

    public Integer getAccountToDepositNumber() {
        return this.accountToDepositNumber;
    }
}
