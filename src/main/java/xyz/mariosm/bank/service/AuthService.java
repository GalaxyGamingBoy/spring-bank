package xyz.mariosm.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountRoles;
import xyz.mariosm.bank.exceptions.InvalidDataException;

import java.util.Arrays;
import java.util.Map;

@Service
public class AuthService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final static String[] invalidUsernames = {"auth"};

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Map<String, Object> register(Account account) {
        if (Arrays.asList(invalidUsernames).contains(account.getUsername()))
            throw new InvalidDataException("Invalid Username!");

        account.setRole(AccountRoles.USER);
        account = accountService.insertAccount(accountService.hashAccount(account));
        String jwt = jwtService.generateToken(account);
        return Map.of("ok", true, "token", jwt);
    }

    public Map<String, Object> login(Account account) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(),
                                                                                   account.getPassword()));
        account = accountService.fetchAccount(account.getUsername(), account.getType());
        String jwt = jwtService.generateToken(account);
        return Map.of("ok", true, "token", jwt);
    }
}
