package xyz.mariosm.bank.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    @GetMapping(path = "/")
    Map<String, Boolean> index() {
        return Map.of("status", true);
    }

    @PostMapping(path = "/register")
    void register() {}

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
