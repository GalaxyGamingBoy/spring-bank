package xyz.mariosm.bank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.ArrayUtils;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountRoles;
import xyz.mariosm.bank.exceptions.InvalidDataException;
import xyz.mariosm.bank.service.AccountService;

import java.util.Arrays;
import java.util.Map;

@Service
public class AuthService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final static String[] invalidUsernames = {"auth"};

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
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
