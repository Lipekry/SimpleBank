package br.com.simpleBank.repository.Interfaces;

import br.com.simpleBank.models.AccountModel;

public interface IAccountRepository {
    AccountModel getAccountByID(String accountID);
    AccountModel insertAccount(String accountID, Integer initialBalance);
    Integer withdraw(String accountID, Integer amount);
    Integer deposit(String accountID, Integer amount);
    void resetAccounts();
}
