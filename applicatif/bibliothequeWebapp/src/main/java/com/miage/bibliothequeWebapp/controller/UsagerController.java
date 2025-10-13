package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.Usager;
import com.miage.bibliothequeWebapp.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsagerController {
    @Autowired
    private UsagerService service;
    @GetMapping("/voirLesUsagers")
    public String readUsagers(Model model) {Iterable<Usager> listUsager = service.getUsagers();
        model.addAttribute("clients", listUsager);
        return "home";
    }

    @GetMapping("/voirUnUsager")
    public String readUsager(Model model, @PathVariable final String nom) {Iterable<Usager> listUsager = service.getUsagers();
        model.addAttribute("clients", listUsager);
        return "home";
    }

    @GetMapping("/formUsager")
    public String createUsager(Model model) {
        Usager u = new Usager();
        model.addAttribute("client", u);
        return "formNewUsager";
    }
    @PostMapping("/addUsager")
    public ModelAndView saveUsager(@ModelAttribute Usager usager) {
        service.saveUsager(usager);
        return new ModelAndView("redirect:/voirLesUsagers");
    }
}
