package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Emprunt;
import com.miage.bibliothequeApp.model.EmpruntId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, EmpruntId> {

}
