package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.EtatExemplaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.bibliothequeWebapp.model.Exemplaire;
import com.miage.bibliothequeWebapp.service.ExemplaireService;
import com.miage.bibliothequeWebapp.service.OeuvreService;

@Controller
public class ExemplaireController {

    @Autowired
    private ExemplaireService service;

    @Autowired
    private OeuvreService oeuvreService;

    @GetMapping("/voirLesExemplaires")
    public String readExemplaires(Model model) {
        Iterable<Exemplaire> list = service.getExemplaires();
        model.addAttribute("exemplaires", list);
        return "exemplaires";
    }

    @GetMapping("/formExemplaire")
    public String createExemplaire(Model model) {
        Exemplaire e = new Exemplaire();
        model.addAttribute("exemplaire", e);
        // Ajouter la liste des oeuvres existantes
        model.addAttribute("oeuvres", oeuvreService.getOeuvres());
        return "formNewExemplaire";
    }

    @PostMapping("/addExemplaire")
    public ModelAndView saveExemplaire(@ModelAttribute Exemplaire exemplaire) {
        exemplaire.setEtat(EtatExemplaire.disponible);
        service.saveExemplaire(exemplaire);
        return new ModelAndView("redirect:/voirLesExemplaires");
    }

    @GetMapping("/voirUnExemplaire/{num}")
    public String readExemplaire(Model model, @PathVariable final Integer num) {
        Exemplaire e = service.getExemplaire(num);
        model.addAttribute("exemplaire", e);
        return "exemplaire";
    }
}
