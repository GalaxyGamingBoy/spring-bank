package xyz.mariosm.bank.service;

import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;
import xyz.mariosm.bank.exceptions.InternalServerException;

public interface AccountService {
    Account hashAccount(Account account);

    Account saveAccount(Account account) throws InternalServerException;

    void deleteAccount(String account);

    Account fetchAccount(String username, AccountTypes type) throws AccountNotFoundException;

    Account updateAccountType(String username, AccountTypes type);
}
