package com.miage.bibliothequeApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miage.bibliothequeApp.model.Usager;
import com.miage.bibliothequeApp.repository.UsagerRepository;

import lombok.Data;

import java.util.Optional;

@Data
@Service
public class UsagerService {

    @Autowired
    private UsagerRepository usagerRepository;

    public Iterable<Usager> getUsagers() {
        return usagerRepository.findAll();
    }

    public Optional<Usager> getUsager(final String nom){return usagerRepository.findById(nom);}

    public Usager addUsager(Usager usager) {
        Usager savedUsager = usagerRepository.save(usager);
        return savedUsager;
    }
}
