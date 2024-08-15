package xyz.mariosm.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.exceptions.InternalServerException;
import xyz.mariosm.bank.service.AccountService;

import java.util.Map;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/")
    Map<String, Boolean> index() {
        return Map.of("status", true);
    }

    @PostMapping(path = "/register")
    Account register(@RequestBody Account account) throws InternalServerException {
        return accountService.insertAccount(accountService.hashAccount(account));
    }

    @PostMapping(path = "/login")
    void login() {}

    @PutMapping(path = "/{id}/type")
    void changeAccountType() {}

    @PutMapping(path = "/{id}/username")
    void changeAccountUsername() {}

    @PutMapping(path = "/{id}/password")
    void changeAccountPassword() {}

    @DeleteMapping(path = "/{id}")
    void deleteAccount() {}

    @DeleteMapping(path = "/{id}/password")
    void resetAccountPassword() {}
}
