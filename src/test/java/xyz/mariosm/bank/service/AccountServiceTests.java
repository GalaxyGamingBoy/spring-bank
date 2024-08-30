package xyz.mariosm.bank.service;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.exceptions.AccountExistsException;
import xyz.mariosm.bank.exceptions.AccountNotFoundException;
import xyz.mariosm.bank.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
@SpringBootTest
@ActiveProfiles({"development"})
public class AccountServiceTests {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Account account;
    private Account dbAccount;

    @BeforeEach
    void setUp() {
        account = new Account("username", "password", AccountTypes.INDIVIDUAL);

        dbAccount = account;
        dbAccount.setId(new ObjectId("66d05ae19d5c805d941cf2d3"));
    }

    @Test
    void assertAccountHashing() {
        final String hash = "$2a$10$LPw1BuMeR9jHIx9.ZNURceR6UcEVHLbv06Lbz8Cly2LVfyb7Wn7S.";
        Mockito.when(passwordEncoder.encode(account.getPassword())).thenReturn(hash);

        Account hashed = accountService.hashAccount(account);
        assertThat(hashed.getPassword()).isEqualTo(hash);
    }

    @Test
    void assertAccountSaving() {
        Mockito.when(accountRepository.save(account)).thenReturn(dbAccount);
        assertThat(accountService.saveAccount(account).getId()).isEqualTo(account.getId());
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);

        Mockito.when(accountRepository.save(account)).thenThrow(new DuplicateKeyException(account.getUsername()));
        assertThrows(AccountExistsException.class, () -> accountService.saveAccount(account));
        Mockito.verify(accountRepository, Mockito.times(2)).save(account);
    }

    @Test
    void assertAccountFetching() {
        Mockito.when(accountRepository.findByUsernameAndType(account.getUsername(), account.getType()))
               .thenReturn(Optional.of(account));
        Mockito.when(accountRepository.findByUsernameAndType(account.getUsername() + "_", account.getType()))
               .thenReturn(Optional.empty());

        assertThat(accountService.fetchAccount(account.getUsername(), account.getType()))
                .isEqualTo(account);

        assertThrows(AccountNotFoundException.class,
                     () -> accountService.fetchAccount(account.getUsername() + "_",
                                                       account.getType()));
    }
}
