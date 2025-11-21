package com.miage.bibliothequeApp.repository;

import com.miage.bibliothequeApp.model.Reservation;
import com.miage.bibliothequeApp.model.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
}
