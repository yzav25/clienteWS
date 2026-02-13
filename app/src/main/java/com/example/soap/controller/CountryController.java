package com.example.soap.controller;

import com.example.soap.client.CountryClient;
import com.example.soap.wsdl.Continent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    private final CountryClient countryClient;

    public CountryController(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @GetMapping("/continents")
    public List<Continent> getContinents() {
        return countryClient.getContinents();
    }
}
