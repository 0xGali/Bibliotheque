package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.UsagerRepository;
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
@DisplayName("Tests unitaires - UsagerService")
class UsagerServiceTest {

    @Mock
    private UsagerRepository usagerRepository;

    @InjectMocks
    private UsagerService usagerService;

    private Usager usager1;
    private Usager usager2;

    @BeforeEach
    void setUp() {
        usager1 = new Usager();
        usager1.setNom("Dupont");
        usager1.setPrenom("Jean");

        usager2 = new Usager();
        usager2.setNom("Martin");
        usager2.setPrenom("Marie");
    }

    @Test
    @DisplayName("Devrait retourner tous les usagers")
    void getUsagers_devraitRetournerTousLesUsagers() {
        // Arrange
        List<Usager> usagers = Arrays.asList(usager1, usager2);
        when(usagerRepository.findAll()).thenReturn(usagers);

        // Act
        Iterable<Usager> result = usagerService.getUsagers();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(usager1, usager2);
        verify(usagerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Devrait retourner liste vide si aucun usager")
    void getUsagers_sansUsagers_devraitRetournerListeVide() {
        // Arrange
        when(usagerRepository.findAll()).thenReturn(List.of());

        // Act
        Iterable<Usager> result = usagerService.getUsagers();

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Devrait retourner un usager par son nom")
    void getUsager_avecNomValide_devraitRetournerUsager() {
        // Arrange
        when(usagerRepository.findById("Dupont"))
            .thenReturn(Optional.of(usager1));

        // Act
        Optional<Usager> result = usagerService.getUsager("Dupont");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getNom()).isEqualTo("Dupont");
        assertThat(result.get().getPrenom()).isEqualTo("Jean");
    }

    @Test
    @DisplayName("Devrait retourner Optional vide si usager inexistant")
    void getUsager_avecNomInexistant_devraitRetournerOptionalVide() {
        // Arrange
        when(usagerRepository.findById("Inexistant"))
            .thenReturn(Optional.empty());

        // Act
        Optional<Usager> result = usagerService.getUsager("Inexistant");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Devrait ajouter un nouvel usager")
    void addUsager_avecUsagerValide_devraitSauvegarder() {
        // Arrange
        when(usagerRepository.save(any(Usager.class))).thenReturn(usager1);

        // Act
        Usager result = usagerService.addUsager(usager1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Dupont");
        assertThat(result.getPrenom()).isEqualTo("Jean");
        verify(usagerRepository, times(1)).save(usager1);
    }

    @Test
    @DisplayName("Devrait retourner l'usager sauvegardé")
    void addUsager_devraitRetournerUsagerComplet() {
        // Arrange
        when(usagerRepository.save(usager2)).thenReturn(usager2);

        // Act
        Usager result = usagerService.addUsager(usager2);

        // Assert
        assertThat(result.getNom()).isEqualTo("Martin");
        assertThat(result.getPrenom()).isEqualTo("Marie");
    }
}
