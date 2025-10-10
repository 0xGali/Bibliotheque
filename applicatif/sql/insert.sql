BEGIN TRANSACTION;

INSERT INTO Oeuvre(titre,auteur,editeur,etat) VALUES
        ('Les Misérables','Victor Hugo','Gallimard','non réservée'),
        ('Le Petit Prince','Antoine de Saint-Exupéry','Larousse','non réservée'),
        ('Harry Potter et la coupe de feu','J.K Rowling','Gallimard','non réservée');
INSERT INTO Usager(nom,prenom) VALUES
    ('BALLOIR','Gael'),
    ('BLEEKER','Maximilien'),
    ('MIESCH','Nathanael'),
    ('JACQUOT','Reika');

INSERT INTO Exemplaire(titre,etat) VALUES
    ('Les Misérables','disponible'),
    ('Les Misérables','disponible'),
    ('Les Misérables','disponible'),
    ('Les Misérables','disponible'),
    ('Le Petit Prince','disponible'),
    ('Le Petit Prince','disponible'),
    ('Le Petit Prince','disponible'),
    ('Harry Potter et la coupe de feu','disponible'),
    ('Harry Potter et la coupe de feu','disponible');

COMMIT;