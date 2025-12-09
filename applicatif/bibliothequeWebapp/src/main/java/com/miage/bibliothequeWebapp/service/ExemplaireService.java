package com.miage.bibliothequeWebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeWebapp.model.Exemplaire;
import com.miage.bibliothequeWebapp.repository.ExemplaireProxy;

@Service
public class ExemplaireService {

    @Autowired
    private ExemplaireProxy proxy;

    public Iterable<Exemplaire> getExemplaires() {
        return proxy.getExemplaires();
    }

    public Exemplaire getExemplaire(final Integer num) {
        return proxy.getExemplaire(num);
    }

    public Exemplaire saveExemplaire(final Exemplaire e) {
        return proxy.createExemplaire(e);
    }
}
