BEGIN TRANSACTION;

CREATE TYPE etatOeuvre AS ENUM ('reservee','nonreservee');

CREATE TABLE Oeuvre(
    titre VARCHAR(50) PRIMARY KEY,
    auteur VARCHAR(50),
    editeur VARCHAR(50),
    etat etatOeuvre,
    nbResa INTEGER
);

CREATE TABLE Usager(
    nom VARCHAR(50) PRIMARY KEY,
    prenom VARCHAR(50)
);

CREATE TABLE Reservation(
    titre_oeuvre VARCHAR(50) REFERENCES Oeuvre(titre),
    nom_usager VARCHAR(50) REFERENCES Usager(nom),
    dateReservation DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY(titre_oeuvre,nom_usager,dateReservation)
);

CREATE TYPE etatExemplaire AS ENUM ('disponible','emprunte');

CREATE TABLE Exemplaire(
    titre_oeuvre VARCHAR(50) REFERENCES Oeuvre(titre),
    numExemplaire serial,
    etat etatExemplaire,
    PRIMARY KEY(titre_oeuvre,numExemplaire)
);

CREATE TABLE Emprunt(
    nom_usager VARCHAR(50) REFERENCES Usager(nom),
    titre_oeuvre_emprunte VARCHAR(50),
    numExemplaire_emprunte INTEGER,
    dateEmprunt DATE,
    PRIMARY KEY(nom_usager,titre_oeuvre_emprunte,numExemplaire_emprunte,dateEmprunt),
    FOREIGN KEY(titre_oeuvre_emprunte, numExemplaire_emprunte) REFERENCES Exemplaire(titre_oeuvre, numExemplaire)
);
 COMMIT;