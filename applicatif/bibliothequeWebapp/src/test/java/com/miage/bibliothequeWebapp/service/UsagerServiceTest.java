package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.Usager;
import com.miage.bibliothequeWebapp.repository.UsagerProxy;
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
@DisplayName("Tests unitaires - UsagerService")
class UsagerServiceTest {

    @Mock
    private UsagerProxy usagerProxy;

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
    @DisplayName("getUsagers - Devrait retourner tous les usagers")
    void getUsagers_devraitRetournerTousLesUsagers() {
        // Arrange
        List<Usager> usagers = Arrays.asList(usager1, usager2);
        when(usagerProxy.getUsagers()).thenReturn(usagers);

        // Act
        Iterable<Usager> result = usagerService.getUsagers();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(usager1, usager2);
        verify(usagerProxy, times(1)).getUsagers();
    }

    @Test
    @DisplayName("getUsager - Devrait retourner un usager par nom")
    void getUsager_avecNomValide_devraitRetournerUsager() {
        // Arrange
        when(usagerProxy.getUsager("Dupont")).thenReturn(usager1);

        // Act
        Usager result = usagerService.getUsager("Dupont");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usager1);
        verify(usagerProxy, times(1)).getUsager("Dupont");
    }

    @Test
    @DisplayName("saveUsager - Devrait sauvegarder un usager")
    void saveUsager_avecUsagerValide_devraitAppelerProxy() {
        // Arrange
        when(usagerProxy.createUsager(any(Usager.class))).thenReturn(usager1);

        // Act
        Usager result = usagerService.saveUsager(usager1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(usager1);
        verify(usagerProxy, times(1)).createUsager(usager1);
    }

    @Test
    @DisplayName("getUsagers - Devrait retourner liste vide")
    void getUsagers_sansUsagers_devraitRetournerListeVide() {
        // Arrange
        when(usagerProxy.getUsagers()).thenReturn(List.of());

        // Act
        Iterable<Usager> result = usagerService.getUsagers();

        // Assert
        assertThat(result).isEmpty();
        verify(usagerProxy, times(1)).getUsagers();
    }
}
