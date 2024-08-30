package xyz.mariosm.bank.service;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.mariosm.bank.data.Account;
import xyz.mariosm.bank.data.AccountTypes;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest
@ActiveProfiles({"testing"})
public class JwtServiceTests {
    @Value("${jwt.claims.issuer}")
    private String issuer;

    @Value("${jwt.claims.duration}")
    private String duration;

    @Autowired
    private JwtService jwtService;

    private Account account;
    private String token;

    @BeforeEach
    void setUp() {
        account = new Account("username", "password", AccountTypes.INDIVIDUAL);
        token = jwtService.generateToken(account);
    }

    @Test
    void assertTokenUsername() {
        assertThat(jwtService.getUsername(token)).isEqualTo(account.getUsername());
    }

    @Test
    void assertTokenVerification() {
        assertThat(jwtService.verifyToken(token, account)).isTrue();
    }

    @Test
    void assertTokenDuration() {
        Date issuedAt = jwtService.extractClaim(token, Claims::getIssuedAt);
        Date expiration = jwtService.extractClaim(token, Claims::getExpiration);
        Long diff = expiration.getTime() - issuedAt.getTime();

        assertThat(diff).isEqualTo(Long.parseLong(duration));
    }

    @Test
    void assertTokenIssuer() {
        assertThat(jwtService.extractClaim(token, Claims::getIssuer)).isEqualTo(issuer);
    }
}
