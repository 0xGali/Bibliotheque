package com.miage.bibliothequeApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EtatOeuvre {
    @JsonProperty("réservée")
    RESERVE("réservée"),
    @JsonProperty("non réservée")
    NON_RESERVE("non réservée");

    private final String value;

    EtatOeuvre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
