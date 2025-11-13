package com.miage.bibliothequeApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeApp.model.Oeuvre;
import com.miage.bibliothequeApp.model.Usager;
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

    public Optional<Oeuvre> getOeuvre(final String titre){return oeuvreRepository.findById(titre);}

    public Oeuvre addOeuvre(Oeuvre oeuvre) {
        Oeuvre savedOeuvre = oeuvreRepository.save(oeuvre);
        return savedOeuvre;
    }
}
