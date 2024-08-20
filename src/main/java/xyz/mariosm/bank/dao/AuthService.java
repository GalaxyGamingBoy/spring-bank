package xyz.mariosm.bank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountRoles;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;
import xyz.mariosm.bank.repository.AccountRepository;
import xyz.mariosm.bank.service.AccountService;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthService(AccountService accountService, JwtService jwtService, AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
    }

    public Map<String, Object> register(Account account) {
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
