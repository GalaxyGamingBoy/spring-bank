package xyz.mariosm.bank.service;

import xyz.mariosm.bank.data.Account;

import java.util.Map;

public interface AuthService {
    Map<String, Object> register(Account account);

    Map<String, Object> login(Account account);
}
