package com.miage.bibliothequeWebapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.miage.bibliothequeWebapp.model.EtatOeuvre;
import com.miage.bibliothequeWebapp.model.Oeuvre;
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
@DisplayName("Tests unitaires - OeuvreProxy")
class OeuvreProxyTest {

    @Autowired
    private OeuvreProxy oeuvreProxy;

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
    @DisplayName("getOeuvres - Devrait appeler GET /getOeuvres")
    void getOeuvres_devraitAppelerAPICorrectement() throws Exception {
        // Arrange
        List<Oeuvre> oeuvres = new ArrayList<>();
        
        Oeuvre oeuvre1 = new Oeuvre();
        oeuvre1.setTitre("Le Seigneur des Anneaux");
        oeuvre1.setAuteur("Tolkien");
        oeuvre1.setEditeur("Gallimard");
        oeuvre1.setEtat(EtatOeuvre.nonreservee);
        oeuvre1.setNbResa(0);
        
        Oeuvre oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Harry Potter");
        oeuvre2.setAuteur("Rowling");
        oeuvre2.setEditeur("Gallimard");
        oeuvre2.setEtat(EtatOeuvre.reservee);
        oeuvre2.setNbResa(2);
        
        oeuvres.add(oeuvre1);
        oeuvres.add(oeuvre2);

        stubFor(get(urlEqualTo("/getOeuvres"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(oeuvres))));

        // Act
        Iterable<Oeuvre> result = oeuvreProxy.getOeuvres();

        // Assert
        List<Oeuvre> resultList = (List<Oeuvre>) result;
        assertThat(resultList).hasSize(2);
        assertThat(resultList.get(0).getTitre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(resultList.get(0).getAuteur()).isEqualTo("Tolkien");
        assertThat(resultList.get(1).getTitre()).isEqualTo("Harry Potter");

        verify(getRequestedFor(urlEqualTo("/getOeuvres")));
    }

    @Test
    @DisplayName("getOeuvres - Devrait retourner liste vide si aucune œuvre")
    void getOeuvres_sansOeuvres_devraitRetournerListeVide() throws Exception {
        // Arrange
        stubFor(get(urlEqualTo("/getOeuvres"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // Act
        Iterable<Oeuvre> result = oeuvreProxy.getOeuvres();

        // Assert
        assertThat(result).isEmpty();
        verify(getRequestedFor(urlEqualTo("/getOeuvres")));
    }

    @Test
    @DisplayName("getOeuvre - Devrait appeler GET /oeuvre/{titre}")
    void getOeuvre_avecTitreValide_devraitRetournerOeuvre() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("1984");
        oeuvre.setAuteur("George Orwell");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);

        stubFor(get(urlEqualTo("/oeuvre/1984"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(oeuvre))));

        // Act
        Oeuvre result = oeuvreProxy.getOeuvre("1984");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("1984");
        assertThat(result.getAuteur()).isEqualTo("George Orwell");
        assertThat(result.getEditeur()).isEqualTo("Gallimard");

        verify(getRequestedFor(urlEqualTo("/oeuvre/1984")));
    }

    @Test
    @DisplayName("getOeuvre - Devrait gérer titre avec espaces")
    void getOeuvre_avecTitreAvecEspaces_devraitEncoderURL() throws Exception {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Le Petit Prince");
        oeuvre.setAuteur("Saint-Exupéry");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);

        // RestTemplate encode les espaces en %20
        stubFor(get(urlEqualTo("/oeuvre/Le%20Petit%20Prince"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(oeuvre))));

        // Act
        Oeuvre result = oeuvreProxy.getOeuvre("Le Petit Prince");

        // Assert
        assertThat(result.getTitre()).isEqualTo("Le Petit Prince");
        verify(getRequestedFor(urlEqualTo("/oeuvre/Le%20Petit%20Prince")));
    }

    @Test
    @DisplayName("createOeuvre - Devrait appeler POST /addOeuvre")
    void createOeuvre_avecOeuvreValide_devraitEnvoyerPOST() throws Exception {
        // Arrange
        Oeuvre nouvelleOeuvre = new Oeuvre();
        nouvelleOeuvre.setTitre("Test Oeuvre");
        nouvelleOeuvre.setAuteur("Test Auteur");
        nouvelleOeuvre.setEditeur("Test Editeur");
        nouvelleOeuvre.setEtat(EtatOeuvre.nonreservee);
        nouvelleOeuvre.setNbResa(0);

        stubFor(post(urlEqualTo("/addOeuvre"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(nouvelleOeuvre))));

        // Act
        Oeuvre result = oeuvreProxy.createOeuvre(nouvelleOeuvre);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Test Oeuvre");
        assertThat(result.getAuteur()).isEqualTo("Test Auteur");

        verify(postRequestedFor(urlEqualTo("/addOeuvre"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    @DisplayName("createOeuvre - Devrait gérer erreur serveur")
    void createOeuvre_avecErreurServeur_devraitLancerException() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Erreur");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");

        stubFor(post(urlEqualTo("/addOeuvre"))
                .willReturn(aResponse()
                        .withStatus(500)));

        // Act & Assert
        org.junit.jupiter.api.Assertions.assertThrows(
            Exception.class,
            () -> oeuvreProxy.createOeuvre(oeuvre)
        );

        verify(postRequestedFor(urlEqualTo("/addOeuvre")));
    }
}
