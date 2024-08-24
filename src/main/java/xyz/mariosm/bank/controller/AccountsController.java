package xyz.mariosm.bank.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.service.AccountService;
import xyz.mariosm.bank.service.AuthService;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.exceptions.InvalidDataException;

import java.util.Map;
import java.util.Objects;

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
    Map<String, Object> register(@RequestBody Account account) {
        System.out.println(account.getRole());
        return authService.register(account);
    }

    @PostMapping(path = "/auth/login")
    Map<String, Object> login(@RequestBody Account account) {
        return authService.login(account);
    }

    @PutMapping(path = "/{user}/type")
    Map<String, Object> updateAccount(@PathVariable("user") String username, @RequestBody AccountTypes type) {
        return Map.of("ok", true, "account", accountService.updateAccountType(username, type));
    }

//    @PutMapping(path = "/{user}/type")
//    void changeAccountType(@PathParam("user") String username, @RequestBody Account account) {
//        if (!Objects.equals(account.getUsername(), username))
//            throw new InvalidDataException("Account and path username mismatch!");
//    }
//
//    @PutMapping(path = "/{user}/username")
//    void changeAccountUsername() {}
//
//    @PutMapping(path = "/{user}/password")
//    void changeAccountPassword() {}

    @DeleteMapping(path = "/{user}")
    void deleteAccount() {}

    @DeleteMapping(path = "/auth/{user}/password")
    void resetAccountPassword() {}
}
