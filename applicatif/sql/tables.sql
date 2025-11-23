BEGIN TRANSACTION;

CREATE TYPE etatOeuvre AS ENUM ('reservee','nonreservee');

CREATE TABLE Oeuvre(
    titre VARCHAR(50) PRIMARY KEY,
    auteur VARCHAR(50),
    editeur VARCHAR(50),
    etat etatOeuvre,
    nb_resa INTEGER DEFAULT 0
);

CREATE TABLE Usager(
    nom VARCHAR(50) PRIMARY KEY,
    prenom VARCHAR(50)
);

CREATE TABLE Reservation(
    titre_oeuvre VARCHAR(50) REFERENCES Oeuvre(titre),
    nom_usager VARCHAR(50) REFERENCES Usager(nom),
    date_reservation DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY(titre_oeuvre,nom_usager,date_reservation)
);

CREATE TYPE etatExemplaire AS ENUM ('disponible','emprunte');

CREATE TABLE Exemplaire(
    titre_oeuvre VARCHAR(50) REFERENCES Oeuvre(titre),
    num_exemplaire serial,
    etat etatExemplaire,
    PRIMARY KEY(titre_oeuvre,num_exemplaire)
);

CREATE TABLE Emprunt(
    nom_usager VARCHAR(50) REFERENCES Usager(nom),
    titre_oeuvre_emprunte VARCHAR(50),
    num_exemplaire_emprunte INTEGER,
    date_emprunt DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY(nom_usager,titre_oeuvre_emprunte,num_exemplaire_emprunte,date_emprunt),
    FOREIGN KEY(titre_oeuvre_emprunte, num_exemplaire_emprunte) REFERENCES Exemplaire(titre_oeuvre, num_exemplaire)
);
 COMMIT;