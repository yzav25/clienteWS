package com.example.soap.controller;

import com.example.soap.client.CountryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    private final CountryClient countryClient;

    public CountryController(CountryClient countryClient) {
        this.countryClient = countryClient;
    }

    @GetMapping("/capital/{countryCode}")
    public String getCapital(@PathVariable String countryCode) {
        return countryClient.getCapitalCity(countryCode);
    }
}
