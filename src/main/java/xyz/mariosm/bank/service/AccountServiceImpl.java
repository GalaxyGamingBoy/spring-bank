package xyz.mariosm.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.exceptions.AccountExistsException;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;
import xyz.mariosm.bank.exceptions.InternalServerException;
import xyz.mariosm.bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account hashAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return account;
    }

    @Override
    public Account saveAccount(Account account) throws InternalServerException {
        try {
            return accountRepository.save(account);
        } catch (Exception ex) {
            if (ex instanceof DuplicateKeyException)
                throw new AccountExistsException(account.getUsername());
            throw new InternalServerException(ex.getMessage());
        }
    }

    @Override
    public void deleteAccount(String account) {
        accountRepository.deleteByUsername(account);
    }

    @Override
    public Account fetchAccount(String username, AccountTypes type) throws AccountNotFoundException {
        return accountRepository.findByUsernameAndType(username, type)
                                .orElseThrow(() -> new AccountNotFoundException(username, type));
    }

    @Override
    public Account updateAccountType(String username, AccountTypes type) {
        Account accountDB = this.accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException(username, type));

        accountDB.setType(type);
        return this.accountRepository.save(accountDB);
    }
}
