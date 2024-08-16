package xyz.mariosm.bank.exceptions;

import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String username, AccountTypes type) {
        super(String.format("The username %s did not match any %s records!", username, type));
    }
}
