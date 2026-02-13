package com.example.soap.controller;

import com.example.soap.client.CountryClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountryClient countryClient;

    @Test
    public void testGetCapital() throws Exception {
        when(countryClient.getCapitalCity("US")).thenReturn("Washington");

        mockMvc.perform(get("/capital/US"))
                .andExpect(status().isOk())
                .andExpect(content().string("Washington"));
    }
}
