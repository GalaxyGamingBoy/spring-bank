package xyz.mariosm.bank.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.bank.http.AccountDetailsRequest;
import xyz.mariosm.bank.http.ChangeTypeRequest;
import xyz.mariosm.bank.service.AccountService;
import xyz.mariosm.bank.service.AuthService;
import xyz.mariosm.bank.data.Account;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    private final AuthService authService;
    private final AccountService accountService;

    @Autowired
    public AccountsController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @GetMapping(path = "/")
    Map<String, Boolean> index() {
        return Map.of("ok", true);
    }

    @PostMapping(path = "/auth/register")
    Map<String, Object> register(@RequestBody AccountDetailsRequest account) {
        return authService.register(new Account(account));
    }

    @PostMapping(path = "/auth/login")
    Map<String, Object> login(@RequestBody AccountDetailsRequest account) {
        return authService.login(new Account(account));
    }

    @PutMapping(path = "/type")
    Map<String, Object> updateAccount(@RequestBody ChangeTypeRequest type) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return Map.of("ok", true, "type",
                      accountService.updateAccountType(username, type.getType()).getType());
    }

    @PutMapping(path = "/password")
    @PreAuthorize("hasRole('ADMIN')")
    Map<String, Boolean> resetAccountPassword(@RequestBody AccountDetailsRequest account) {
        Account db = accountService.fetchAccount(account.getUsername(), account.getType());
        db.setPassword(account.getPassword());
        accountService.saveAccount(accountService.hashAccount(db));

        return Map.of("ok", true);
    }

    @DeleteMapping(path = "/")
    Map<String, Boolean> deleteAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        accountService.deleteAccount(username);

        return Map.of("ok", true);
    }
}
