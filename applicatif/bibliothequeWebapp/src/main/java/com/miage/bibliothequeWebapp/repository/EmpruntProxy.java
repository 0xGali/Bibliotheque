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
import com.miage.bibliothequeWebapp.model.Emprunt;

@Slf4j
@Component
public class EmpruntProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<Emprunt> getEmprunts() {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/getEmprunts";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Emprunt>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Emprunt>>() {}
        );
        log.debug("Get Emprunts call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Emprunt createEmprunt(Emprunt e) {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/addEmprunt";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Emprunt> request = new HttpEntity<Emprunt>(e);
        ResponseEntity<Emprunt> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Emprunt.class);
        log.debug("Create Emprunt call " + response.getStatusCode().toString());
        return response.getBody();
    }
}
