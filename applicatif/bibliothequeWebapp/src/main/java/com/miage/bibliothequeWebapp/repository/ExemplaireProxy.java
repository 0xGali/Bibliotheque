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
import com.miage.bibliothequeWebapp.model.Exemplaire;

@Slf4j
@Component
public class ExemplaireProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<Exemplaire> getExemplaires() {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/getExemplaires";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Exemplaire>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Exemplaire>>() {}
        );
        log.debug("Get Exemplaires call " + response.getStatusCode().toString());
        Iterable<Exemplaire> exemplaires = response.getBody();
        if (exemplaires != null) {
            int count = 0;
            for (@SuppressWarnings("unused") Exemplaire ex : exemplaires) {
                count++;
            }
            log.info("Exemplaires retrieved: {} items", count);
        } else {
            log.warn("No exemplaires returned from API");
        }
        return exemplaires;
    }

    public Exemplaire getExemplaire(Integer num) {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/exemplaire/" + num;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Exemplaire> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Exemplaire.class
        );
        log.debug("Get Exemplaire call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Exemplaire createExemplaire(Exemplaire e) {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/addExemplaire";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Exemplaire> request = new HttpEntity<Exemplaire>(e);
        ResponseEntity<Exemplaire> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Exemplaire.class);
        log.debug("Create Exemplaire call " + response.getStatusCode().toString());
        return response.getBody();
    }
}
