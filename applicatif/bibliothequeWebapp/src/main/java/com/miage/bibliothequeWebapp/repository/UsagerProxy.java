package com.miage.bibliothequeWebapp.repository;

import com.miage.bibliothequeWebapp.configuration.CustomProperties;
import com.miage.bibliothequeWebapp.model.Usager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class UsagerProxy {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(
            UsagerProxy.class);

    @Autowired
    private CustomProperties props;

    public Iterable<Usager> getUsagers() {
        String baseApiUrl = props.getApiUrl();
        String getUsagersUrl = baseApiUrl + "/getUsagers";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Usager>> response = restTemplate.exchange(
                getUsagersUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Usager>>() {}
        );
        log.debug("Get Usagers call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Usager getUsager(String nom) {
        String baseApiUrl = props.getApiUrl();
        String getUsagerUrl = baseApiUrl + "/usager/" + nom;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Usager> response = restTemplate.exchange(
                getUsagerUrl,
                HttpMethod.GET,
                null,
                Usager.class
        );
        log.debug("Get Usager call " + response.getStatusCode().toString());
        return response.getBody();
    }

    public Usager createUsager(Usager e) {
        String baseApiUrl = props.getApiUrl();
        String createUsagerUrl = baseApiUrl + "/addUsager";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Usager> request = new HttpEntity<Usager>(e);
        ResponseEntity<Usager> response = restTemplate.exchange(
                createUsagerUrl,
                HttpMethod.POST,
                request,
                Usager.class);

        log.debug("Create Usager call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
