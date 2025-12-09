package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.EtatOeuvre;
import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.repository.OeuvreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - OeuvreService")
class OeuvreServiceTest {

    @Mock
    private OeuvreRepository oeuvreRepository;

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
        oeuvre1.setNbResa(0);

        oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Harry Potter");
        oeuvre2.setAuteur("Rowling");
        oeuvre2.setEditeur("Gallimard");
        oeuvre2.setEtat(EtatOeuvre.reservee);
        oeuvre2.setNbResa(2);
    }

    @Test
    @DisplayName("Devrait retourner toutes les œuvres")
    void getOeuvres_devraitRetournerToutesLesOeuvres() {
        // Arrange
        List<Oeuvre> oeuvres = Arrays.asList(oeuvre1, oeuvre2);
        when(oeuvreRepository.findAll()).thenReturn(oeuvres);

        // Act
        Iterable<Oeuvre> result = oeuvreService.getOeuvres();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(oeuvre1, oeuvre2);
        verify(oeuvreRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner liste vide si aucune œuvre")
    void getOeuvres_sansOeuvres_devraitRetournerListeVide() {
        // Arrange
        when(oeuvreRepository.findAll()).thenReturn(List.of());

        // Act
        Iterable<Oeuvre> result = oeuvreService.getOeuvres();

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Devrait retourner une œuvre par son titre")
    void getOeuvre_avecTitreValide_devraitRetournerOeuvre() {
        // Arrange
        when(oeuvreRepository.findById("Le Seigneur des Anneaux"))
            .thenReturn(Optional.of(oeuvre1));

        // Act
        Optional<Oeuvre> result = oeuvreService.getOeuvre("Le Seigneur des Anneaux");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getTitre()).isEqualTo("Le Seigneur des Anneaux");
        assertThat(result.get().getAuteur()).isEqualTo("Tolkien");
    }

    @Test
    @DisplayName("Devrait retourner Optional vide si œuvre inexistante")
    void getOeuvre_avecTitreInexistant_devraitRetournerOptionalVide() {
        // Arrange
        when(oeuvreRepository.findById("Titre Inexistant"))
            .thenReturn(Optional.empty());

        // Act
        Optional<Oeuvre> result = oeuvreService.getOeuvre("Titre Inexistant");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Devrait ajouter une nouvelle œuvre")
    void addOeuvre_avecOeuvreValide_devraitSauvegarder() {
        // Arrange
        when(oeuvreRepository.save(any(Oeuvre.class))).thenReturn(oeuvre1);

        // Act
        Oeuvre result = oeuvreService.addOeuvre(oeuvre1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Le Seigneur des Anneaux");
        verify(oeuvreRepository, times(1)).save(oeuvre1);
    }

    @Test
    @DisplayName("Devrait retourner l'œuvre sauvegardée avec tous ses attributs")
    void addOeuvre_devraitRetournerOeuvreComplete() {
        // Arrange
        when(oeuvreRepository.save(oeuvre2)).thenReturn(oeuvre2);

        // Act
        Oeuvre result = oeuvreService.addOeuvre(oeuvre2);

        // Assert
        assertThat(result.getTitre()).isEqualTo("Harry Potter");
        assertThat(result.getAuteur()).isEqualTo("Rowling");
        assertThat(result.getEditeur()).isEqualTo("Gallimard");
        assertThat(result.getEtat()).isEqualTo(EtatOeuvre.reservee);
        assertThat(result.getNbResa()).isEqualTo(2);
    }
}
