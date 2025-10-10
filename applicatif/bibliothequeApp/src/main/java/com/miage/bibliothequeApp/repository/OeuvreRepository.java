package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Oeuvre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface OeuvreRepository extends JpaRepository<Oeuvre, String>{

}
