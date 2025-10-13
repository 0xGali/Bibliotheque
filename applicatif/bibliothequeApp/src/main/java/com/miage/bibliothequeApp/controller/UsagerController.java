package com.miage.bibliothequeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.service.UsagerService;

import java.util.Optional;

@RestController
public class UsagerController {

    @Autowired
    UsagerService usagerService;

    @GetMapping("/getUsagers")
    public Iterable<Usager> getUsagers(){
        return usagerService.getUsagers();
    }

    @GetMapping("/usager/{id}")
    public Usager getUsager(@PathVariable("nom") final String nom) {
        Optional<Usager> usager = usagerService.getUsager(nom);
        if (usager.isPresent()) {
            return usager.get();
        } else {
            return null;
        }
    }

    @PostMapping("/addUsager")
    public Usager addUsager(@RequestBody final Usager usager) {
        return usagerService.addUsager(usager);
    }
}
