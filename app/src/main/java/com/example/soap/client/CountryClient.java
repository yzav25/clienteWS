package com.example.soap.client;

import com.example.soap.wsdl.Continent;
import com.example.soap.wsdl.ListOfContinentsByNameResponseType;
import com.example.soap.wsdl.ListOfContinentsByNameType;
import com.example.soap.wsdl.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class CountryClient {

    private final WebServiceTemplate webServiceTemplate;
    private final ObjectFactory objectFactory;

    public CountryClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
        this.objectFactory = new ObjectFactory();
    }

    @SuppressWarnings("unchecked")
    public List<Continent> getContinents() {
        ListOfContinentsByNameType request = objectFactory.createListOfContinentsByNameType();
        JAXBElement<ListOfContinentsByNameType> requestElement = objectFactory.createListOfContinentsByName(request);

        Object response = webServiceTemplate.marshalSendAndReceive(requestElement);

        ListOfContinentsByNameResponseType responseType;
        if (response instanceof JAXBElement) {
            responseType = ((JAXBElement<ListOfContinentsByNameResponseType>) response).getValue();
        } else {
            responseType = (ListOfContinentsByNameResponseType) response;
        }

        return responseType.getListOfContinentsByNameResult().getTContinent();
    }
}
