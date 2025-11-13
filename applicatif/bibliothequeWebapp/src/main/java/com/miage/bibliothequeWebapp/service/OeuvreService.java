package com.miage.bibliothequeWebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeWebapp.model.Oeuvre;
import com.miage.bibliothequeWebapp.repository.OeuvreProxy;

@Service
public class OeuvreService {
    @Autowired
    private OeuvreProxy oeuvreProxy;
    public Oeuvre getOeuvre(final String titre) {
        return oeuvreProxy.getOeuvre(titre);
    }
    public Iterable<Oeuvre> getOeuvres() {
        return oeuvreProxy.getOeuvres();
    }

    public Oeuvre saveOeuvre(final Oeuvre oeuvre) {
        oeuvreProxy.createOeuvre(oeuvre);
        return oeuvre;
    }
}
