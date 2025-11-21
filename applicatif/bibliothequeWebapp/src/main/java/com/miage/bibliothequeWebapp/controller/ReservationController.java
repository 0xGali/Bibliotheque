package com.miage.bibliothequeWebapp.controller;

import com.miage.bibliothequeWebapp.model.ReservationId;
import com.miage.bibliothequeWebapp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping("/faireUneReservation")
    public ModelAndView faireUneReservation(@ModelAttribute ReservationId reservationId) {
        reservationId.setDate_reservation(new Date());
        service.faireUneReservation(reservationId);
        return new ModelAndView("redirect:/voirUnUsager/"+reservationId.getNom_usager());
    }
}
