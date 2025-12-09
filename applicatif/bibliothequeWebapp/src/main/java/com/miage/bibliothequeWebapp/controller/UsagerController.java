package com.miage.bibliothequeWebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.model.Usager;
import com.miage.bibliothequeWebapp.service.OeuvreService;
import com.miage.bibliothequeWebapp.service.UsagerService;

@Controller
public class UsagerController {
    @Autowired
    private UsagerService service;
    @Autowired
    private OeuvreService oeuvreService;

    @GetMapping("/voirLesUsagers")
    public String readUsagers(Model model) {Iterable<Usager> listUsager = service.getUsagers();
        model.addAttribute("usagers", listUsager);
        return "usagers";
    }

    @GetMapping("/voirUnUsager/{nom}")
    public String readUsager(Model model, @PathVariable final String nom) {
        Usager usager = service.getUsager(nom);
        //on ajoute aussi les oeuvres pour le formulaire de reservation
        ReservationId reservationId = new ReservationId();
        reservationId.setNomUsager(nom);
        model.addAttribute("oeuvres", oeuvreService.getOeuvres());
        model.addAttribute("usager", usager);
        model.addAttribute("reservation", reservationId);
        return "usager";
    }

    @GetMapping("/formUsager")
    public String createUsager(Model model) {
        Usager u = new Usager();
        model.addAttribute("usager", u);
        return "formNewUsager";
    }
    @PostMapping("/addUsager")
    public ModelAndView saveUsager(@ModelAttribute Usager usager) {
        service.saveUsager(usager);
        return new ModelAndView("redirect:/voirLesUsagers");
    }
}
