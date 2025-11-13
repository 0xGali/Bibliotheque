package com.miage.bibliothequeWebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.bibliothequeWebapp.model.Oeuvre;
import com.miage.bibliothequeWebapp.service.OeuvreService;

public class OeuvreController {
    @Autowired
    private OeuvreService service;
    @GetMapping("/voirLesOeuvres")
    public String readOeuvres(Model model) {Iterable<Oeuvre> listOeuvre = service.getOeuvres();
        model.addAttribute("oeuvres", listOeuvre);
        return "oeuvres";
    }

    @GetMapping("/voirUneOeuvre/{titre}")
    public String readOeuvre(Model model, @PathVariable final String titre) {
        Oeuvre oeuvre = service.getOeuvre(titre);
        model.addAttribute("oeuvre", oeuvre);
        return "oeuvre";
    }

    @GetMapping("/formOeuvre")
    public String createOeuvre(Model model) {
        Oeuvre u = new Oeuvre();
        model.addAttribute("oeuvre", u);
        return "formNewOeuvre";
    }
    @PostMapping("/addOeuvre")
    public ModelAndView saveOeuvre(@ModelAttribute Oeuvre oeuvre) {
        service.saveOeuvre(oeuvre);
        return new ModelAndView("redirect:/voirLesOeuvres");
    }
}
