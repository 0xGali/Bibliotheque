package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Usager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DisplayName("Tests unitaires - UsagerRepository")
class UsagerRepositoryTest {

    @Autowired
    private UsagerRepository usagerRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Devrait sauvegarder un usager")
    void save_avecUsagerValide_devraitPersister() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Durand");
        usager.setPrenom("Pierre");

        // Act
        Usager saved = usagerRepository.save(usager);
        entityManager.flush();

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getNom()).isEqualTo("Durand");
        assertThat(saved.getPrenom()).isEqualTo("Pierre");
    }

    @Test
    @DisplayName("Devrait trouver un usager par son nom")
    void findById_avecNomExistant_devraitRetournerUsager() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("Lefebvre");
        usager.setPrenom("Sophie");
        entityManager.persist(usager);
        entityManager.flush();

        // Act
        Optional<Usager> found = usagerRepository.findById("Lefebvre");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getPrenom()).isEqualTo("Sophie");
    }

    @Test
    @DisplayName("Devrait retourner Optional vide pour nom inexistant")
    void findById_avecNomInexistant_devraitRetournerVide() {
        // Act
        Optional<Usager> found = usagerRepository.findById("Inexistant");

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Devrait retourner tous les usagers")
    void findAll_devraitRetournerTousLesUsagers() {
        // Arrange
        Usager usager1 = new Usager();
        usager1.setNom("Nom1");
        usager1.setPrenom("Prenom1");

        Usager usager2 = new Usager();
        usager2.setNom("Nom2");
        usager2.setPrenom("Prenom2");

        entityManager.persist(usager1);
        entityManager.persist(usager2);
        entityManager.flush();

        // Act
        List<Usager> usagers = usagerRepository.findAll();

        // Assert
        assertThat(usagers).hasSize(2);
        assertThat(usagers).extracting(Usager::getNom)
            .containsExactlyInAnyOrder("Nom1", "Nom2");
    }

    @Test
    @DisplayName("Devrait mettre à jour un usager")
    void save_avecMiseAJour_devraitPersister() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("TestUpdate");
        usager.setPrenom("AncienPrenom");
        entityManager.persist(usager);
        entityManager.flush();

        // Act
        usager.setPrenom("NouveauPrenom");
        Usager updated = usagerRepository.save(usager);
        entityManager.flush();

        // Assert
        Optional<Usager> found = usagerRepository.findById("TestUpdate");
        assertThat(found).isPresent();
        assertThat(found.get().getPrenom()).isEqualTo("NouveauPrenom");
    }

    @Test
    @DisplayName("Devrait supprimer un usager")
    void delete_avecUsagerExistant_devraitSupprimer() {
        // Arrange
        Usager usager = new Usager();
        usager.setNom("ASupprimer");
        usager.setPrenom("Test");
        entityManager.persist(usager);
        entityManager.flush();

        // Act
        usagerRepository.delete(usager);
        entityManager.flush();

        // Assert
        Optional<Usager> found = usagerRepository.findById("ASupprimer");
        assertThat(found).isEmpty();
    }
}
