package com.bankapplication.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private TransactionType type;
    private Double amount;
    @ManyToOne
    private Account account;

    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE", nullable = false, updatable = false, insertable=false)
    private Date createdAt;

    public Transaction() {
    }

    public Transaction(TransactionType type, Double amount, Account account) {
        this.type = type;
        this.amount = amount;
        this.account = account;
    }
}
