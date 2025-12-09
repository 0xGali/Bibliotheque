package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {

}
