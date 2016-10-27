package com.test.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by min on 2015/1/23.
 */
//@Entity
//@Table(name = "account")
public class AccountEntity implements Serializable {

    private long id;

    private String name;

    private String accountNo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

}
