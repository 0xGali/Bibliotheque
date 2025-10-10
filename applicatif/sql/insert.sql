BEGIN TRANSACTION;

INSERT INTO Oeuvre(titre,auteur,editeur,etat, nbResa) VALUES
        ('Les Misérables','Victor Hugo','Gallimard','nonreservee',0),
        ('Le Petit Prince','Antoine de Saint-Exupéry','Larousse','nonreservee',0),
        ('Harry Potter et la coupe de feu','J.K Rowling','Gallimard','nonreservee',0);
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