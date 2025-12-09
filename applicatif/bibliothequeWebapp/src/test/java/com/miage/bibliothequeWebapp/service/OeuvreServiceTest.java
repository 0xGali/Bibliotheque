package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.EtatOeuvre;
import com.miage.bibliothequeWebapp.model.Oeuvre;
import com.miage.bibliothequeWebapp.repository.OeuvreProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - OeuvreService")
class OeuvreServiceTest {

    @Mock
    private OeuvreProxy oeuvreProxy;

    @InjectMocks
    private OeuvreService oeuvreService;

    private Oeuvre oeuvre1;
    private Oeuvre oeuvre2;

    @BeforeEach
    void setUp() {
        oeuvre1 = new Oeuvre();
        oeuvre1.setTitre("Le Seigneur des Anneaux");
        oeuvre1.setAuteur("Tolkien");
        oeuvre1.setEditeur("Gallimard");
        oeuvre1.setEtat(EtatOeuvre.nonreservee);

        oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Harry Potter");
        oeuvre2.setAuteur("Rowling");
        oeuvre2.setEditeur("Gallimard");
        oeuvre2.setEtat(EtatOeuvre.reservee);
    }

    @Test
    @DisplayName("getOeuvres - Devrait retourner toutes les œuvres")
    void getOeuvres_devraitRetournerToutesLesOeuvres() {
        // Arrange
        List<Oeuvre> oeuvres = Arrays.asList(oeuvre1, oeuvre2);
        when(oeuvreProxy.getOeuvres()).thenReturn(oeuvres);

        // Act
        Iterable<Oeuvre> result = oeuvreService.getOeuvres();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(oeuvre1, oeuvre2);
        verify(oeuvreProxy, times(1)).getOeuvres();
    }

    @Test
    @DisplayName("getOeuvre - Devrait retourner une œuvre par titre")
    void getOeuvre_avecTitreValide_devraitRetournerOeuvre() {
        // Arrange
        when(oeuvreProxy.getOeuvre("1984")).thenReturn(oeuvre1);

        // Act
        Oeuvre result = oeuvreService.getOeuvre("1984");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(oeuvre1);
        verify(oeuvreProxy, times(1)).getOeuvre("1984");
    }

    @Test
    @DisplayName("saveOeuvre - Devrait sauvegarder une œuvre")
    void saveOeuvre_avecOeuvreValide_devraitAppelerProxy() {
        // Arrange
        when(oeuvreProxy.createOeuvre(any(Oeuvre.class))).thenReturn(oeuvre1);

        // Act
        Oeuvre result = oeuvreService.saveOeuvre(oeuvre1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(oeuvre1);
        verify(oeuvreProxy, times(1)).createOeuvre(oeuvre1);
    }

    @Test
    @DisplayName("getOeuvres - Devrait retourner liste vide")
    void getOeuvres_sansOeuvres_devraitRetournerListeVide() {
        // Arrange
        when(oeuvreProxy.getOeuvres()).thenReturn(List.of());

        // Act
        Iterable<Oeuvre> result = oeuvreService.getOeuvres();

        // Assert
        assertThat(result).isEmpty();
        verify(oeuvreProxy, times(1)).getOeuvres();
    }
}
