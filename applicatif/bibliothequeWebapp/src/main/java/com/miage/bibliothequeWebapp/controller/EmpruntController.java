package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.Emprunt;
import com.miage.bibliothequeWebapp.model.EmpruntId;
import com.miage.bibliothequeWebapp.model.Exemplaire;
import com.miage.bibliothequeWebapp.service.EmpruntService;
import com.miage.bibliothequeWebapp.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.miage.bibliothequeWebapp.repository.UsagerProxyEmprunt;

@Controller
public class EmpruntController {

    @Autowired
    private EmpruntService empruntService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private UsagerProxyEmprunt usagerProxyEmprunt;

    @GetMapping("/voirLesEmprunts")
    public String readEmprunts(Model model) {
        Iterable<Emprunt> list = empruntService.getEmprunts();
        model.addAttribute("emprunts", list);
        return "emprunts";
    }

    @GetMapping("/formEmprunt")
    public String formEmprunt(Model model) {
        Emprunt e = new Emprunt();
        EmpruntId id = new EmpruntId();
        e.setId(id);
        model.addAttribute("emprunt", e);
        // ajouter la liste d'exemplaires disponibles pour sélectionner
        model.addAttribute("exemplaires", exemplaireService.getExemplaires());
        // ajouter la liste d'usagers
        model.addAttribute("usagers", usagerProxyEmprunt.getUsagers());
        return "formNewEmprunt";
    }

    @PostMapping("/addEmpruntWeb")
    public ModelAndView saveEmprunt(@ModelAttribute Emprunt emprunt) {
        empruntService.saveEmprunt(emprunt);
        return new ModelAndView("redirect:/voirLesEmprunts");
    }
}
