package com.miage.bibliothequeWebapp.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.miage.bibliothequeWebapp.configuration.CustomProperties;
import com.miage.bibliothequeWebapp.model.Oeuvre;

@Slf4j
@Component
public class OeuvreProxy {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(
            OeuvreProxy.class);

    @Autowired
    private CustomProperties props;

    public Iterable<Oeuvre> getOeuvres() {
        String baseApiUrl = props.getApiUrl();
        String getOeuvresUrl = baseApiUrl + "/getOeuvres";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Oeuvre>> response = restTemplate.exchange(
                getOeuvresUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Oeuvre>>() {}
        );
        log.debug("Get Oeuvres call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Oeuvre getOeuvre(String nom) {
        String baseApiUrl = props.getApiUrl();
        String getOeuvreUrl = baseApiUrl + "/oeuvre/" + nom;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Oeuvre> response = restTemplate.exchange(
                getOeuvreUrl,
                HttpMethod.GET,
                null,
                Oeuvre.class
        );
        log.debug("Get Oeuvre call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Oeuvre createOeuvre(Oeuvre e) {
        String baseApiUrl = props.getApiUrl();
        String createOeuvreUrl = baseApiUrl + "/addOeuvre";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Oeuvre> request = new HttpEntity<Oeuvre>(e);
        ResponseEntity<Oeuvre> response = restTemplate.exchange(
                createOeuvreUrl,
                HttpMethod.POST,
                request,
                Oeuvre.class);

        log.debug("Create Oeuvre call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
