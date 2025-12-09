package com.miage.bibliothequeWebapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.miage.bibliothequeWebapp.model.Usager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "com.miage.bibliothequewebapp.api-url=http://localhost:8089"
})
@DisplayName("Tests unitaires - UsagerProxy")
class UsagerProxyTest {

    @Autowired
    private UsagerProxy usagerProxy;

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
    @DisplayName("getUsagers - Devrait appeler GET /getUsagers")
    void getUsagers_devraitAppelerAPICorrectement() throws Exception {
        // Arrange
        List<Usager> usagers = new ArrayList<>();
        
        Usager usager1 = new Usager();
        usager1.setNom("Dupont");
        usager1.setPrenom("Jean");
        
        Usager usager2 = new Usager();
        usager2.setNom("Martin");
        usager2.setPrenom("Marie");
        
        usagers.add(usager1);
        usagers.add(usager2);

        stubFor(get(urlEqualTo("/getUsagers"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(usagers))));

        // Act
        Iterable<Usager> result = usagerProxy.getUsagers();

        // Assert
        List<Usager> resultList = (List<Usager>) result;
        assertThat(resultList).hasSize(2);
        assertThat(resultList.get(0).getNom()).isEqualTo("Dupont");
        assertThat(resultList.get(0).getPrenom()).isEqualTo("Jean");

        verify(getRequestedFor(urlEqualTo("/getUsagers")));
    }

    @Test
    @DisplayName("getUsager - Devrait appeler GET /usager/{nom}")
    void getUsager_avecNomValide_devraitRetournerUsager() throws Exception {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Dupont");
        usager.setPrenom("Jean");

        stubFor(get(urlEqualTo("/usager/Dupont"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(usager))));

        // Act
        Usager result = usagerProxy.getUsager("Dupont");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Dupont");
        assertThat(result.getPrenom()).isEqualTo("Jean");

        verify(getRequestedFor(urlEqualTo("/usager/Dupont")));
    }

    @Test
    @DisplayName("createUsager - Devrait appeler POST /addUsager")
    void createUsager_avecUsagerValide_devraitEnvoyerPOST() throws Exception {
        // Arrange
        Usager nouvelUsager = new Usager();
        nouvelUsager.setNom("Durand");
        nouvelUsager.setPrenom("Pierre");

        stubFor(post(urlEqualTo("/addUsager"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(nouvelUsager))));

        // Act
        Usager result = usagerProxy.createUsager(nouvelUsager);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Durand");
        assertThat(result.getPrenom()).isEqualTo("Pierre");

        verify(postRequestedFor(urlEqualTo("/addUsager")));
    }

    @Test
    @DisplayName("getUsagers - Devrait retourner liste vide")
    void getUsagers_sansUsagers_devraitRetournerListeVide() {
        // Arrange
        stubFor(get(urlEqualTo("/getUsagers"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Act
        Iterable<Usager> result = usagerProxy.getUsagers();

        // Assert
        assertThat(result).isEmpty();
        verify(getRequestedFor(urlEqualTo("/getUsagers")));
    }
}
