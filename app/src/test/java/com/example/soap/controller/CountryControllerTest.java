package com.example.soap.controller;

import com.example.soap.client.CountryClient;
import com.example.soap.wsdl.Continent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CountryClient countryClient;

    @Test
    public void testGetContinents() throws Exception {
        Continent africa = new Continent();
        africa.setSCode("AF");
        africa.setSName("Africa");

        Continent europe = new Continent();
        europe.setSCode("EU");
        europe.setSName("Europe");

        when(countryClient.getContinents()).thenReturn(Arrays.asList(africa, europe));

        mockMvc.perform(get("/continents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scode").value("AF"))
                .andExpect(jsonPath("$[0].sname").value("Africa"))
                .andExpect(jsonPath("$[1].scode").value("EU"))
                .andExpect(jsonPath("$[1].sname").value("Europe"));
    }
}
