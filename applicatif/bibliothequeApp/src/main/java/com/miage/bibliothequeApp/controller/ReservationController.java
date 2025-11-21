package com.miage.bibliothequeApp.controller;

import com.miage.bibliothequeApp.model.ReservationId;
import com.miage.bibliothequeApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/faireUneReservation")
    public ReservationId faireUneReservation(@RequestBody final ReservationId reservationId) {
        return reservationService.faireUneReservation(reservationId);
    }

}
