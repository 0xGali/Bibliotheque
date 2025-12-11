package com.miage.bibliothequeApp.controller;

import com.miage.bibliothequeApp.model.EmpruntId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miage.bibliothequeApp.model.Emprunt;
import com.miage.bibliothequeApp.service.EmpruntService;

@RestController
public class EmpruntController {

	@Autowired
	private EmpruntService empruntService;

	@GetMapping("/getEmprunts")
	public Iterable<Emprunt> getEmprunts(){
		return empruntService.getEmprunts();
	}

	@PostMapping("/addEmprunt")
	public Emprunt addEmprunt(@RequestBody final EmpruntId empruntid) {
		return empruntService.addEmprunt(empruntid);
	}

	@PostMapping("/deleteEmprunt")
	public String deleteEmprunt(@RequestBody final EmpruntId empruntid) {
		empruntService.deleteEmprunt(empruntid);
		return "Emprunt deleted successfully";
	}
	
}
