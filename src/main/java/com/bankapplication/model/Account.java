package com.bankapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique=true, nullable=false)
    private Integer number;
    private Double balance;

    public Account() {
    }

    public Account(Integer number, Double balance) {
        this.number = number;
        this.balance = balance;
    }

    public boolean debit(Double ammount) {
        if(ammount <= this.balance) {
            this.balance = this.balance - ammount;
            return true;
        }

        return false;
    }

    public boolean deposit(Double ammount) {
        this.balance = this.balance + ammount;

        return true;
    }

    public Double getBalance() {
        return this.balance;
    }
}
