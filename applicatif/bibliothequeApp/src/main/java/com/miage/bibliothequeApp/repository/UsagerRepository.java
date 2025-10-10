package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Usager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsagerRepository extends JpaRepository<Usager, String>{

}
