package xyz.mariosm.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;
import xyz.mariosm.bank.exceptions.InternalServerException;
import xyz.mariosm.bank.exceptions.InvalidDataException;
import xyz.mariosm.bank.service.AccountService;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    private final AccountService accountService;
    private final static String[] invalidUsernames = {"auth"};

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/")
    Map<String, Boolean> index() {
        return Map.of("ok", true);
    }

    @PostMapping(path = "/auth/register")
    Map<String, Object> register(@RequestBody Account account) throws InternalServerException, InvalidDataException {
        if (Arrays.asList(invalidUsernames).contains(account.getUsername()))
            throw new InvalidDataException(account.getUsername());

        account = accountService.insertAccount(accountService.hashAccount(account));
        return Map.of("ok", true, "account", account);
    }

    @PostMapping(path = "/auth/login")
    Map<String, Object> login(@RequestBody Account account) throws AccountNotFoundException, AccessDeniedException {
        Account databaseRecord = accountService.fetchAccount(account.getUsername(), account.getType());
        if (!accountService.checkAccountPassword(databaseRecord, account.getPassword()))
            throw new AccessDeniedException("Incorrect Username/Password Provided");

        return Map.of("ok", true, "jwt", "");
    }

    @PutMapping(path = "/{user}/type")
    void changeAccountType() {}

    @PutMapping(path = "/{user}/username")
    void changeAccountUsername() {}

    @PutMapping(path = "/{user}/password")
    void changeAccountPassword() {}

    @DeleteMapping(path = "/{user}")
    void deleteAccount() {}

    @DeleteMapping(path = "/auth/{user}/password")
    void resetAccountPassword() {}
}
