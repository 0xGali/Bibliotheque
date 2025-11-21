package com.miage.bibliothequeWebapp.service;

import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.repository.ReservationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationProxy reservationProxy;

    public ReservationId faireUneReservation(final ReservationId reservationId) {
        reservationProxy.faireUneReservation(reservationId);
        return reservationId;
    }

}
