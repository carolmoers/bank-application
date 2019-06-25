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
}
