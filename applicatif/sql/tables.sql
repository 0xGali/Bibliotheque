BEGIN TRANSACTION;

CREATE TYPE etatOeuvre AS ENUM ('réservée','non réservée');

CREATE TABLE Oeuvre(
    titre VARCHAR(50) PRIMARY KEY,
    auteur VARCHAR(50),
    editeur VARCHAR(50),
    etat etatOeuvre
);

CREATE TABLE Usager(
    nom VARCHAR(50) PRIMARY KEY,
    prenom VARCHAR(50)
);

CREATE TABLE Reservation(
    titre VARCHAR(50) REFERENCES Oeuvre(titre),
    nom VARCHAR(50) REFERENCES Usager(nom),
    dateReservation DATE,
    PRIMARY KEY(titre,nom,dateReservation)
);

CREATE TYPE etatExemplaire AS ENUM ('disponible','emprunté');

CREATE TABLE Exemplaire(
    titre VARCHAR(50) REFERENCES Oeuvre(titre),
    numExemplaire serial,
    etat etatExemplaire,
    PRIMARY KEY(titre,numExemplaire)
);

CREATE TABLE Emprunt(
    nom VARCHAR(50) REFERENCES Usager(nom),
    titre VARCHAR(50),
    numExemplaire INTEGER,
    dateEmprunt DATE,
    PRIMARY KEY(nom,titre,numExemplaire,dateEmprunt),
    FOREIGN KEY(titre, numExemplaire) REFERENCES Exemplaire(titre, numExemplaire)
);
 COMMIT;