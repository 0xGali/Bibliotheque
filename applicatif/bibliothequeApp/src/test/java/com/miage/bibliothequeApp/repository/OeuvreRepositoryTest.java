package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.EtatOeuvre;
import com.miage.bibliothequeApp.model.Oeuvre;
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
@DisplayName("Tests unitaires - OeuvreRepository")
class OeuvreRepositoryTest {

    @Autowired
    private OeuvreRepository oeuvreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Devrait sauvegarder une œuvre")
    void save_avecOeuvreValide_devraitPersister() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("1984");
        oeuvre.setAuteur("George Orwell");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);

        // Act
        Oeuvre saved = oeuvreRepository.save(oeuvre);
        entityManager.flush();

        // Assert
        assertThat(saved).isNotNull();
        assertThat(saved.getTitre()).isEqualTo("1984");
        assertThat(saved.getAuteur()).isEqualTo("George Orwell");
    }

    @Test
    @DisplayName("Devrait trouver une œuvre par son titre")
    void findById_avecTitreExistant_devraitRetournerOeuvre() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Le Petit Prince");
        oeuvre.setAuteur("Saint-Exupéry");
        oeuvre.setEditeur("Gallimard");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);
        entityManager.flush();

        // Act
        Optional<Oeuvre> found = oeuvreRepository.findById("Le Petit Prince");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getAuteur()).isEqualTo("Saint-Exupéry");
    }

    @Test
    @DisplayName("Devrait retourner Optional vide pour titre inexistant")
    void findById_avecTitreInexistant_devraitRetournerVide() {
        // Act
        Optional<Oeuvre> found = oeuvreRepository.findById("Titre Inexistant");

        // Assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Devrait retourner toutes les œuvres")
    void findAll_devraitRetournerToutesLesOeuvres() {
        // Arrange
        Oeuvre oeuvre1 = new Oeuvre();
        oeuvre1.setTitre("Oeuvre 1");
        oeuvre1.setAuteur("Auteur 1");
        oeuvre1.setEditeur("Editeur 1");
        oeuvre1.setEtat(EtatOeuvre.nonreservee);
        oeuvre1.setNbResa(0);

        Oeuvre oeuvre2 = new Oeuvre();
        oeuvre2.setTitre("Oeuvre 2");
        oeuvre2.setAuteur("Auteur 2");
        oeuvre2.setEditeur("Editeur 2");
        oeuvre2.setEtat(EtatOeuvre.reservee);
        oeuvre2.setNbResa(1);

        entityManager.persist(oeuvre1);
        entityManager.persist(oeuvre2);
        entityManager.flush();

        // Act
        List<Oeuvre> oeuvres = oeuvreRepository.findAll();

        // Assert
        assertThat(oeuvres).hasSize(2);
        assertThat(oeuvres).extracting(Oeuvre::getTitre)
            .containsExactlyInAnyOrder("Oeuvre 1", "Oeuvre 2");
    }

    @Test
    @DisplayName("Devrait mettre à jour l'état d'une œuvre")
    void save_avecMiseAJourEtat_devraitPersister() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Test Update");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);
        entityManager.flush();

        // Act
        oeuvre.setEtat(EtatOeuvre.reservee);
        oeuvre.setNbResa(1);
        Oeuvre updated = oeuvreRepository.save(oeuvre);
        entityManager.flush();

        // Assert
        Optional<Oeuvre> found = oeuvreRepository.findById("Test Update");
        assertThat(found).isPresent();
        assertThat(found.get().getEtat()).isEqualTo(EtatOeuvre.reservee);
        assertThat(found.get().getNbResa()).isEqualTo(1);
    }

    @Test
    @DisplayName("Devrait supprimer une œuvre")
    void delete_avecOeuvreExistante_devraitSupprimer() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("A Supprimer");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.nonreservee);
        oeuvre.setNbResa(0);
        entityManager.persist(oeuvre);
        entityManager.flush();

        // Act
        oeuvreRepository.delete(oeuvre);
        entityManager.flush();

        // Assert
        Optional<Oeuvre> found = oeuvreRepository.findById("A Supprimer");
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Devrait persister les enums correctement")
    void save_avecEnums_devraitPersisterCorrectement() {
        // Arrange
        Oeuvre oeuvre = new Oeuvre();
        oeuvre.setTitre("Test Enum");
        oeuvre.setAuteur("Auteur");
        oeuvre.setEditeur("Editeur");
        oeuvre.setEtat(EtatOeuvre.reservee);
        oeuvre.setNbResa(5);

        // Act
        oeuvreRepository.save(oeuvre);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Oeuvre> found = oeuvreRepository.findById("Test Enum");
        assertThat(found).isPresent();
        assertThat(found.get().getEtat()).isEqualTo(EtatOeuvre.reservee);
    }
}
