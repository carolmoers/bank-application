package com.bankapplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true, nullable=false)
    private Integer number;
    private Double balance;

    public Account(Integer number, Double balance) {
        this.number = number;
        this.balance = balance;
    }

    public Double getBalance() {
        return this.balance;
    }

    public boolean debit(Double ammount) {
        if(ammount < this.balance) {
            this.balance = this.balance - ammount;
            return true;
        }

        return false;
    }

    public boolean deposit(Double ammount) {
        this.balance = this.balance + ammount;

        return true;
    }
}
