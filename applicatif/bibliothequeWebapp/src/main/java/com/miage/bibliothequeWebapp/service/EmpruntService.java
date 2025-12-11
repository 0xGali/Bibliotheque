package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.EmpruntId;
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

    public EmpruntId saveEmprunt(final EmpruntId eid) {
        return proxy.createEmprunt(eid);
    }

    public void deleteEmprunt(final EmpruntId eid) {
        proxy.deleteEmprunt(eid);
    }
}
