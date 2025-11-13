package com.miage.bibliothequeApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.service.OeuvreService;

@RestController
public class OeuvreController {

    @Autowired
    OeuvreService oeuvreService;

    @GetMapping("/getOeuvres")
    public Iterable<Oeuvre> getOeuvres(){
        return oeuvreService.getOeuvres();
    }

    @GetMapping("/oeuvre/{titre}")
    public Oeuvre getOeuvre(@PathVariable("titre") final String titre) {
        Optional<Oeuvre> oeuvre = oeuvreService.getOeuvre(titre);
        if (oeuvre.isPresent()) {
            return oeuvre.get();
        } else {
            return null;
        }
    }

    @PostMapping("/addOeuvre")
    public Oeuvre addOeuvre(@RequestBody final Oeuvre oeuvre) {
        return oeuvreService.addOeuvre(oeuvre);
    }
}
