package br.com.simpleBank.models;

public class AccountModel {
    String id;
    Integer balance;

    public AccountModel(String id, Integer balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balanceInCents) {
        this.balance = balanceInCents;
    }
}
