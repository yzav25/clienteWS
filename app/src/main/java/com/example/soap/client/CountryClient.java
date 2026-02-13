package com.example.soap.client;

import com.example.soap.wsdl.CapitalCity;
import com.example.soap.wsdl.CapitalCityResponse;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class CountryClient {

    private final WebServiceTemplate webServiceTemplate;

    public CountryClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public String getCapitalCity(String countryISOCode) {
        CapitalCity request = new CapitalCity();
        request.setSCountryISOCode(countryISOCode);

        CapitalCityResponse response = (CapitalCityResponse) webServiceTemplate
                .marshalSendAndReceive(request);

        return response.getCapitalCityResult();
    }
}
