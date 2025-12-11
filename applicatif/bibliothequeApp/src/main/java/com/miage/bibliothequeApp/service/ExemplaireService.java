package com.miage.bibliothequeApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeApp.model.Exemplaire;
import com.miage.bibliothequeApp.repository.ExemplaireRepository;

import lombok.Data;

@Data
@Service
public class ExemplaireService {

	@Autowired
	private ExemplaireRepository exemplaireRepository;

	public Iterable<Exemplaire> getExemplaires() {
		return exemplaireRepository.findAll();
	}

	public Optional<Exemplaire> getExemplaire(final Integer num) {
		return exemplaireRepository.findById(num);
	}

	public Exemplaire addExemplaire(Exemplaire exemplaire) {
		return exemplaireRepository.save(exemplaire);
	}

	public Exemplaire saveExemplaire(Exemplaire exemplaire) {
		return exemplaireRepository.save(exemplaire);
	}

	public void deleteExemplaire(final Integer num) {
		exemplaireRepository.deleteById(num);
	}
}
