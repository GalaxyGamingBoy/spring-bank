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
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account hashAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return account;
    }

    public Account insertAccount(Account account) throws InternalServerException {
        try {
            return accountRepository.save(account);
        } catch (Exception ex) {
            if (ex instanceof DuplicateKeyException)
                throw new AccountExistsException(account.getUsername());
            throw new InternalServerException(ex.getMessage());
        }
    }

    public Account fetchAccount(String username, AccountTypes type) throws AccountNotFoundException {
        return accountRepository.findByUsernameAndType(username, type)
                                .orElseThrow(() -> new AccountNotFoundException(username, type));
    }
}
