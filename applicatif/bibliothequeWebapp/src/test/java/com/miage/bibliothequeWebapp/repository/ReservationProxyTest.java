package com.miage.bibliothequeWebapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.miage.bibliothequeWebapp.model.ReservationId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "com.miage.bibliothequewebapp.api-url=http://localhost:8089"
})
@DisplayName("Tests unitaires - ReservationProxy")
class ReservationProxyTest {

    @Autowired
    private ReservationProxy reservationProxy;

    private WireMockServer wireMockServer;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("faireUneReservation - Devrait appeler POST /faireUneReservation")
    void faireUneReservation_avecDonneesValides_devraitEnvoyerPOST() throws Exception {
        // Arrange
        Date now = new Date();
        ReservationId reservationId = new ReservationId(
            "Le Seigneur des Anneaux",
            "Dupont",
            now
        );

        stubFor(post(urlEqualTo("/faireUneReservation"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(reservationId))));

        // Act
        ReservationId result = reservationProxy.faireUneReservation(reservationId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitreOeuvre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(result.getNomUsager()).isEqualTo("Dupont");

        verify(postRequestedFor(urlEqualTo("/faireUneReservation")));
    }

    @Test
    @DisplayName("faireUneReservation - Devrait gérer erreur 400")
    void faireUneReservation_avecErreur_devraitLancerException() {
        // Arrange
        ReservationId reservationId = new ReservationId("Oeuvre", "Usager", new Date());

        stubFor(post(urlEqualTo("/faireUneReservation"))
                .willReturn(aResponse()
                        .withStatus(400)));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(
            Exception.class,
            () -> reservationProxy.faireUneReservation(reservationId)
        );

        verify(postRequestedFor(urlEqualTo("/faireUneReservation")));
    }
}
