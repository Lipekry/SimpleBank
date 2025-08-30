package br.com.simpleBank.repository.Implementations;

import br.com.simpleBank.models.AccountModel;
import br.com.simpleBank.repository.Interfaces.IAccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryInMemory implements IAccountRepository {

    private static final List<AccountModel> accounts = new ArrayList<>();

    @Override
    public Integer withdraw(String accountID, Integer amount) {
        AccountModel accountModel = this.getAccountByID(accountID);
        accountModel.setBalance(accountModel.getBalance() - amount);
        return accountModel.getBalance();
    }

    @Override
    public Integer deposit(String accountID, Integer amount) {
        AccountModel accountModel = this.getAccountByID(accountID);
        accountModel.setBalance(accountModel.getBalance() + amount);
        return accountModel.getBalance();
    }

    @Override
    public AccountModel getAccountByID(String accountID) {
        for (AccountModel acc : accounts) {
            if (acc.getId().equals(accountID)) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public AccountModel insertAccount(String accountID, Integer initialBalance) {
        AccountModel account = new AccountModel(accountID ,initialBalance);
        accounts.add(account);
        return account;
    }

    @Override
    public void resetAccounts() {
        accounts.clear();
    }
}
