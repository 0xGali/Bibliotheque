CREATE TABLE OEUVRE (
    titre VARCHAR(50) PRIMARY KEY,
    auteur VARCHAR(50),
    editeur VARCHAR(50),
    etat VARCHAR(20) CHECK (etat IN ('reservee','nonreservee')),
    nb_resa INT
);

CREATE TABLE USAGER (
    nom VARCHAR(50) PRIMARY KEY,
    prenom VARCHAR(50)
);

CREATE TABLE RESERVATION (
    titre_oeuvre VARCHAR(50),
    nom_usager VARCHAR(50),
    date_reservation DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY(titre_oeuvre, nom_usager, date_reservation),
    FOREIGN KEY(titre_oeuvre) REFERENCES OEUVRE(titre),
    FOREIGN KEY(nom_usager) REFERENCES USAGER(nom)
);

CREATE TABLE EXEMPLAIRE (
    titre_oeuvre VARCHAR(50),
    num_exemplaire INT AUTO_INCREMENT,
    etat VARCHAR(20) CHECK (etat IN ('disponible','emprunte')),
    PRIMARY KEY(titre_oeuvre, num_exemplaire),
    FOREIGN KEY(titre_oeuvre) REFERENCES OEUVRE(titre)
);

CREATE TABLE EMPRUNT (
    nom_usager VARCHAR(50),
    titre_oeuvre_emprunte VARCHAR(50),
    num_exemplaire_emprunte INT,
    date_emprunt DATE,
    PRIMARY KEY(nom_usager, num_exemplaire_emprunte, date_emprunt),
    FOREIGN KEY(nom_usager) REFERENCES USAGER(nom),
    FOREIGN KEY(titre_oeuvre_emprunte, num_exemplaire_emprunte)
        REFERENCES EXEMPLAIRE(titre_oeuvre, num_exemplaire)
);
