package com.miage.bibliothequeApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.service.UsagerService;

@RestController
public class UsagerController {

    @Autowired
    UsagerService usagerService;

    @GetMapping("/getUsagers")
    public Iterable<Usager> getUsagers(){
        return usagerService.getUsagers();
    }
}
