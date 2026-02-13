package com.example.soap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SoapConfig {

    /**
     * Custom WebServiceMessageFactory to handle mock services that might return
     * incorrect Content-Type (e.g., application/xml instead of text/xml).
     */
    @Bean
    public WebServiceMessageFactory messageFactory() {
        SaajSoapMessageFactory saajFactory = new SaajSoapMessageFactory();
        saajFactory.afterPropertiesSet();
        return new WebServiceMessageFactory() {
            @Override
            public WebServiceMessage createWebServiceMessage() {
                return saajFactory.createWebServiceMessage();
            }

            @Override
            public WebServiceMessage createWebServiceMessage(InputStream inputStream) throws IOException {
                try {
                    MessageFactory messageFactory = MessageFactory.newInstance();
                    MimeHeaders mimeHeaders = new MimeHeaders();
                    // Force text/xml to satisfy SAAJ for SOAP 1.1
                    mimeHeaders.addHeader("Content-Type", "text/xml");
                    SOAPMessage soapMessage = messageFactory.createMessage(mimeHeaders, inputStream);
                    return new SaajSoapMessage(soapMessage);
                } catch (SOAPException e) {
                    throw new IOException("Could not create SOAP message", e);
                }
            }
        };
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.soap.wsdl");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller, WebServiceMessageFactory messageFactory) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        // Using the mock service URI provided by the user
        webServiceTemplate.setDefaultUri("https://soap-service-free.mock.beeceptor.com/CountryInfoService.wso");
        return webServiceTemplate;
    }
}
