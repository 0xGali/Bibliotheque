package com.miage.bibliothequeApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miage.bibliothequeApp.model.Exemplaire;
import com.miage.bibliothequeApp.service.ExemplaireService;

@RestController
public class ExemplaireController {

	@Autowired
	private ExemplaireService exemplaireService;

	@GetMapping("/getExemplaires")
	public Iterable<Exemplaire> getExemplaires(){
		return exemplaireService.getExemplaires();
	}

	@GetMapping("/exemplaire/{num}")
	public Exemplaire getExemplaire(@PathVariable("num") final Integer num) {
		Optional<Exemplaire> exemplaire = exemplaireService.getExemplaire(num);
		return exemplaire.orElse(null);
	}

	@PostMapping("/addExemplaire")
	public Exemplaire addExemplaire(@RequestBody final Exemplaire exemplaire) {
		return exemplaireService.addExemplaire(exemplaire);
	}
}
