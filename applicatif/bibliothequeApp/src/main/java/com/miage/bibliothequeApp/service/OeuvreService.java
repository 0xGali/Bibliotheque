package com.miage.bibliothequeApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.repository.OeuvreRepository;

import lombok.Data;

@Data
@Service
public class OeuvreService {

    @Autowired
    private OeuvreRepository oeuvreRepository;

    public Iterable<Oeuvre> getOeuvres() {
        return oeuvreRepository.findAll();
    }
}
