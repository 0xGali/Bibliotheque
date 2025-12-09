package com.miage.bibliothequeWebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeWebapp.model.Emprunt;
import com.miage.bibliothequeWebapp.repository.EmpruntProxy;

@Service
public class EmpruntService {

    @Autowired
    private EmpruntProxy proxy;

    public Iterable<Emprunt> getEmprunts() {
        return proxy.getEmprunts();
    }

    public Emprunt saveEmprunt(final Emprunt e) {
        return proxy.createEmprunt(e);
    }
}
