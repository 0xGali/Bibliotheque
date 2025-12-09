package com.miage.bibliothequeWebapp.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.miage.bibliothequeWebapp.configuration.CustomProperties;
import com.miage.bibliothequeWebapp.model.Usager;

@Slf4j
@Component
public class UsagerProxyEmprunt {

    @Autowired
    private CustomProperties props;

    public Iterable<Usager> getUsagers() {
        String baseApiUrl = props.getApiUrl();
        String url = baseApiUrl + "/getUsagers";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Usager>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Usager>>() {}
        );
        log.debug("Get Usagers call " + response.getStatusCode().toString());
        return response.getBody();
    }
}
