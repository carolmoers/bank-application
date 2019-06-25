package com.bankapplication.controller;

import com.bankapplication.model.Account;
import com.bankapplication.repository.AccountRepository;
import com.bankapplication.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {
    private static final String TRANSFER_URL =
            "/api/transfer";
    private static final int ACCOUNT_TO_DEBIT_NUMBER = 890;
    private static final int ACCOUNT_TO_DEPOSIT_NUMBER = 456;
    private Account accountToDebit;
    private Account accountToDeposit;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        accountToDebit = new Account(ACCOUNT_TO_DEBIT_NUMBER, Double.valueOf(150));
        accountRepository.save(accountToDebit);
        accountToDeposit = new Account(ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(50));
        accountRepository.save(accountToDeposit);
    }

    @After
    public void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void shouldTransferSuccessfully() throws Exception {
        TransferInformation transferInformation =
                new TransferInformation(ACCOUNT_TO_DEBIT_NUMBER, ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(50));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(transferInformation);
        mockMvc.perform(post(TRANSFER_URL)
                .content(jsonString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value("Transfer from account [890] to account [456] completed with success"));
    }

    @Test
    public void shouldHandleErrorWhenInvalidTransferInformationIsPassed() throws Exception {
        TransferInformation transferInformation =
                new TransferInformation(null, ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(50));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(transferInformation);
        mockMvc.perform(post(TRANSFER_URL)
                .content(jsonString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Transfer information with null value"));
    }

    @Test
    public void shouldHandleErrorWhenTransferFailed() throws Exception {
        TransferInformation transferInformation =
                new TransferInformation(ACCOUNT_TO_DEBIT_NUMBER, ACCOUNT_TO_DEPOSIT_NUMBER, Double.valueOf(200));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(transferInformation);
        mockMvc.perform(post(TRANSFER_URL)
                .content(jsonString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$")
                        .value("Transfer failed: Some error happened during the transfer"));
    }
}