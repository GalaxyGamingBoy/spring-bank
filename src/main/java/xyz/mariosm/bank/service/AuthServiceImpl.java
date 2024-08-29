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
public class AuthServiceImpl implements AuthService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final static String[] invalidUsernames = {"auth"};

    @Autowired
    public AuthServiceImpl(AccountService accountService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Map<String, Object> register(Account account) {
        if (Arrays.asList(invalidUsernames).contains(account.getUsername()))
            throw new InvalidDataException("Invalid Username!");

        account.setRole(AccountRoles.USER);
        account = accountService.saveAccount(accountService.hashAccount(account));
        String jwt = jwtService.generateToken(account);
        return Map.of("ok", true, "token", jwt);
    }

    @Override
    public Map<String, Object> login(Account account) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(),
                                                                                   account.getPassword()));
        account = accountService.fetchAccount(account.getUsername(), account.getType());
        String jwt = jwtService.generateToken(account);
        return Map.of("ok", true, "token", jwt);
    }
}
