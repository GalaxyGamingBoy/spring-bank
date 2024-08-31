package xyz.mariosm.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import xyz.mariosm.bank.data.AccountTypes;
import xyz.mariosm.bank.http.AccountDetailsRequest;
import xyz.mariosm.bank.http.ChangeTypeRequest;
import xyz.mariosm.bank.service.AccountService;

import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"testing"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountsControllerTests {
    static private AccountDetailsRequest accountDetails;
    static private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @BeforeAll
    static void beforeAll() {
        accountDetails = AccountDetailsRequest.builder()
                                              .username(RandomString.make(16))
                                              .password(RandomString.make(16))
                                              .type(AccountTypes.INDIVIDUAL)
                                              .build();

        objectMapper = new ObjectMapper();
    }

    private String login() throws Exception {
        MvcResult result = mockMvc.perform(post("/accounts/auth/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(objectMapper.writeValueAsString(accountDetails)))
                                  .andExpect(status().isOk())
                                  .andExpect(jsonPath("$.ok", isA(Boolean.class), is(true)).exists())
                                  .andExpect(jsonPath("$.token", isA(String.class)))
                                  .andReturn();

        HashMap<String, Object> data = objectMapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        return (String) data.get("token");
    }

    @Test
    @Order(1)
    void assertRegister() throws Exception {
        mockMvc.perform(post("/accounts/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountDetails)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.ok").value(true))
               .andExpect(jsonPath("$.token").isString());

        accountService.promoteAdmin(accountDetails);
    }

    @Test
    @Order(2)
    void assertLogin() throws Exception {
        assertThat(login()).isNotNull();
    }

    @Test
    @Order(3)
    void assertChangeAccountType() throws Exception {
        String token = login();

        ChangeTypeRequest changeTypeRequest = ChangeTypeRequest.builder().type(AccountTypes.BUSINESS).build();

        mockMvc.perform(put("/accounts/type")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(changeTypeRequest))
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true))
                .andExpect(jsonPath("$.type").value("BUSINESS"));
    }

    @Test
    @Order(4)
    void assertResetPassword() throws Exception {
        String token = login();

        mockMvc.perform(put("/accounts/password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountDetails))
                                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true));
    }

    @Test
    @Order(5)
    void assertDelete() throws Exception {
        String token = login();

        mockMvc.perform(delete("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true));
    }
}
