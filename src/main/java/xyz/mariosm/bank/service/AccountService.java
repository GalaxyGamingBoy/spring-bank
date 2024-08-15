package xyz.mariosm.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.exceptions.AccountExistsException;
import xyz.mariosm.bank.exceptions.InternalServerException;
import xyz.mariosm.bank.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account hashAccount(Account account) {
        String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt(10));
        account.setPassword(hashedPassword);
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
}
