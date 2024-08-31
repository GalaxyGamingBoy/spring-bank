package xyz.mariosm.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.http.AccountDetailsRequest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"testing"})
public class AccountsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    static private AccountDetailsRequest accountDetails;
    static private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        accountDetails = AccountDetailsRequest.builder()
                                              .username(RandomString.make(16))
                                              .password(RandomString.make(16))
                                              .type(AccountTypes.INDIVIDUAL)
                                              .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void assertRegister() throws Exception {
        mockMvc.perform(post("/accounts/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", isA(Boolean.class), is(true)).exists())
                .andExpect(jsonPath("$.token", isA(String.class)));
    }

    @Test
    void assertLogin() throws Exception {
        mockMvc.perform(post("/accounts/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountDetails)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.ok", isA(Boolean.class), is(true)).exists())
               .andExpect(jsonPath("$.token", isA(String.class)));
    }

    //    @Test
//    void assertLogin() throws Exception {
//        mockMvc.perform(post("/accounts/auth/login")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(accountDetails)))
//                .andDo(print());
//    }
}
