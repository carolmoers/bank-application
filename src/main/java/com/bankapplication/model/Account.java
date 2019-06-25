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
    @Column(updatable = false, nullable = false)
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

    public void debit(Double amount) {
        if(amount <= this.balance) {
            this.balance = this.balance - amount;
        } else {
          throw new IllegalArgumentException("Not enough money to debit");
        }
    }

    public void deposit(Double amount) {
        this.balance = this.balance + amount;
    }

    public Double getBalance() {
        return this.balance;
    }
}
