package com.miage.bibliothequeWebapp.repository;

import com.miage.bibliothequeWebapp.configuration.CustomProperties;
import com.miage.bibliothequeWebapp.model.ReservationId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ReservationProxy {

    @Autowired
    private CustomProperties props;

    public ReservationId faireUneReservation(ReservationId id) {
        String baseApiUrl = props.getApiUrl();
        String createUsagerUrl = baseApiUrl + "/faireUneReservation";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReservationId> request = new HttpEntity<>(id);
        ResponseEntity<ReservationId> response = restTemplate.exchange(
                createUsagerUrl,
                HttpMethod.POST,
                request,
                ReservationId.class);

        log.debug("Create Reservation call " + response.getStatusCode().toString());

        return response.getBody();
    }
}
