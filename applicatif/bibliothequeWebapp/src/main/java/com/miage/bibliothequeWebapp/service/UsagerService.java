package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.Usager;
import com.miage.bibliothequeWebapp.repository.UsagerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsagerService {
    @Autowired
    private UsagerProxy usagerProxy;
    public Usager getUsager(final String nom) {
        return usagerProxy.getUsager(nom);
    }
    public Iterable<Usager> getUsagers() {
        return usagerProxy.getUsagers();
    }

    public Usager saveUsager(final Usager usager) {
        usagerProxy.createUsager(usager);
        return usager;
    }
}
