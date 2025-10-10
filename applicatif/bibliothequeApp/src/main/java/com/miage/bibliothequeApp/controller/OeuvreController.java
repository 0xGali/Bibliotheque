package com.miage.bibliothequeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
