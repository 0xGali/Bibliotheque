package com.miage.bibliothequeApp.service;

import com.miage.bibliothequeApp.model.*;
import com.miage.bibliothequeApp.repository.*;
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
	private OeuvreRepository oeuvreRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	public Iterable<Emprunt> getEmprunts() {
		return empruntRepository.findAll();
	}

	public Emprunt addEmprunt(EmpruntId empruntid) {
		log.debug("Creating emprunt for user={}, exemplaire={}", empruntid.getNom(), empruntid.getNumExemplaire());
		
		if (empruntid == null) {
			throw new RuntimeException("Id d'emprunt invalide");
		}

		// Vérifier que l'usager existe
		String nomUsager = empruntid.getNom();
		if (nomUsager == null || nomUsager.isEmpty()) {
			throw new RuntimeException("Nom d'usager manquant");
		}
		if (!usagerRepository.existsById(nomUsager)) {
			throw new RuntimeException("Usager '" + nomUsager + "' inexistant");
		}

		if (empruntid.getNumExemplaire() == null) {
			throw new RuntimeException("Numéro d'exemplaire manquant dans l'emprunt");
		}


		Optional<Exemplaire> optEx = exemplaireRepository.findById(Math.toIntExact(empruntid.getNumExemplaire()));
		Exemplaire ex = optEx.orElseThrow(() -> new RuntimeException("Exemplaire introuvable"));

		if (ex.getEtat() == null || ex.getEtat() != EtatExemplaire.disponible) {
			throw new RuntimeException("Exemplaire non disponible pour emprunt");
		}

		// Compléter le titre de l'emprunt avant de l'enregistrer
		Emprunt emprunt = new Emprunt(empruntid,ex.getTitreOeuvre());

		// Sauvegarder l'emprunt
		Emprunt savedEmprunt = empruntRepository.save(emprunt);

		// Mettre à jour l'état de l'exemplaire
		ex.setEtat(EtatExemplaire.emprunte);
		exemplaireRepository.save(ex);

		// Supprimer la réservation si elle existe pour cet usager et cette oeuvre
		try {
			ReservationId resId = new ReservationId(ex.getTitreOeuvre(), nomUsager, null);
			// Chercher si une réservation existe pour ce titre et cet usager
			Iterable<Reservation> allReservations = reservationRepository.findAll();
			for (Reservation res : allReservations) {
				if (res.getId().getTitreOeuvre().equals(ex.getTitreOeuvre()) &&
					res.getId().getNomUsager().equals(nomUsager)) {
					reservationRepository.delete(res);
					Oeuvre oeuvre = oeuvreRepository.findById(res.getId().getTitreOeuvre()).get();
					oeuvre.setNbResa(oeuvre.getNbResa() - 1);
					if(oeuvre.getNbResa() <= 0) {
						oeuvre.setEtat(EtatOeuvre.nonreservee);
					}
					oeuvreRepository.save(oeuvre);
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
