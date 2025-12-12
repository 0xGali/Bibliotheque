-- Usagers
INSERT INTO USAGER(NOM, PRENOM) VALUES ('BALLOIR','Gael');
INSERT INTO USAGER(NOM, PRENOM) VALUES ('BLEEKER','Maximilien');
INSERT INTO USAGER(NOM, PRENOM) VALUES ('MIESCH','Nathanael');
INSERT INTO USAGER(NOM, PRENOM) VALUES ('JACQUOT','Reika');

-- Œuvres
INSERT INTO Oeuvre(titre, auteur, editeur, etat, nb_resa) VALUES
('Les Misérables','Victor Hugo','Gallimard','nonreservee',0);
INSERT INTO Oeuvre(titre, auteur, editeur, etat, nb_resa) VALUES
('Le Petit Prince','Antoine de Saint-Exupéry','Larousse','nonreservee',0);
INSERT INTO Oeuvre(titre, auteur, editeur, etat, nb_resa) VALUES
('Harry Potter et la coupe de feu','J.K Rowling','Gallimard','nonreservee',0);

-- Exemplaires
INSERT INTO Exemplaire(titre_oeuvre, etat) VALUES ('Les Misérables','disponible');
INSERT INTO Exemplaire(titre_oeuvre, etat) VALUES ('Les Misérables','disponible');
INSERT INTO Exemplaire(titre_oeuvre, etat) VALUES ('Le Petit Prince','disponible');
INSERT INTO Exemplaire(titre_oeuvre, etat) VALUES ('Harry Potter et la coupe de feu','disponible');

-- Réservations
INSERT INTO Reservation(titre_oeuvre, nom_usager) VALUES ('Les Misérables','JACQUOT');
