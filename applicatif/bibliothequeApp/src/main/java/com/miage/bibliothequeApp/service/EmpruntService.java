package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.Emprunt;
import com.miage.bibliothequeApp.model.Exemplaire;
import com.miage.bibliothequeApp.model.EmpruntId;
import com.miage.bibliothequeApp.model.EtatExemplaire;
import com.miage.bibliothequeApp.model.Reservation;
import com.miage.bibliothequeApp.model.ReservationId;
import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.EmpruntRepository;
import com.miage.bibliothequeApp.repository.ExemplaireRepository;
import com.miage.bibliothequeApp.repository.ReservationRepository;
import com.miage.bibliothequeApp.repository.UsagerRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Data
@Service
@Transactional
@Slf4j
public class EmpruntService {

	@Autowired
	private EmpruntRepository empruntRepository;

	@Autowired
	private ExemplaireRepository exemplaireRepository;

	@Autowired
	private UsagerRepository usagerRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public Iterable<Emprunt> getEmprunts() {
		return empruntRepository.findAll();
	}

	public Emprunt addEmprunt(Emprunt emprunt) {
		log.debug("Creating emprunt for user={}, exemplaire={}", emprunt.getId().getNom(), emprunt.getId().getNumExemplaire());
		
		if (emprunt == null || emprunt.getId() == null) {
			throw new RuntimeException("Emprunt ou id d'emprunt invalide");
		}

		// Vérifier que l'usager existe
		String nomUsager = emprunt.getId().getNom();
		if (nomUsager == null || nomUsager.isEmpty()) {
			throw new RuntimeException("Nom d'usager manquant");
		}
		if (!usagerRepository.existsById(nomUsager)) {
			throw new RuntimeException("Usager '" + nomUsager + "' inexistant");
		}

		Long numEx = emprunt.getId().getNumExemplaire();
		if (numEx == null) {
			throw new RuntimeException("Numéro d'exemplaire manquant dans l'emprunt");
		}

		Integer numExInt = numEx.intValue();

		Optional<Exemplaire> optEx = exemplaireRepository.findById(numExInt);
		Exemplaire ex = optEx.orElseThrow(() -> new RuntimeException("Exemplaire introuvable"));

		if (ex.getEtat() == null || ex.getEtat() != EtatExemplaire.disponible) {
			throw new RuntimeException("Exemplaire non disponible pour emprunt");
		}

		// Compléter le titre de l'emprunt avant de l'enregistrer
		emprunt.setTitreOeuvre(ex.getTitreOeuvre());

		// Sauvegarder l'emprunt
		Emprunt savedEmprunt = empruntRepository.save(emprunt);
		log.debug("Emprunt created with ID: {}", savedEmprunt.getId());

		// Mettre à jour l'état de l'exemplaire
		ex.setEtat(EtatExemplaire.emprunte);
		exemplaireRepository.save(ex);
		log.debug("Exemplaire {} marked as emprunte", numExInt);

		// Supprimer la réservation si elle existe pour cet usager et cette oeuvre
		try {
			ReservationId resId = new ReservationId(ex.getTitreOeuvre(), nomUsager, null);
			// Chercher si une réservation existe pour ce titre et cet usager
			Iterable<Reservation> allReservations = reservationRepository.findAll();
			for (Reservation res : allReservations) {
				if (res.getId().getTitreOeuvre().equals(ex.getTitreOeuvre()) &&
					res.getId().getNomUsager().equals(nomUsager)) {
					reservationRepository.delete(res);
					log.debug("Reservation cancelled for user={}, title={}", nomUsager, ex.getTitreOeuvre());
					break;
				}
			}
		} catch (Exception e) {
			log.warn("Failed to delete reservation: {}", e.getMessage());
			// Ne pas bloquer l'emprunt si la suppression de réservation échoue
		}

		return savedEmprunt;
	}
}
