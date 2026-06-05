ALTER TABLE livraison
    ADD CONSTRAINT fk_livraison_client
        FOREIGN KEY (client_id) REFERENCES client(id);

ALTER TABLE livraison
    ADD CONSTRAINT fk_livraison_chauffeur
        FOREIGN KEY (chauffeur_id) REFERENCES chauffeur(id);

ALTER TABLE livraison
    ADD CONSTRAINT fk_livraison_vehicule
        FOREIGN KEY (vehicule_id) REFERENCES vehicule(id);

ALTER TABLE vehicule
    ALTER COLUMN statut SET DEFAULT 'DISPONIBLE';

ALTER TABLE chauffeur
    ALTER COLUMN disponible SET DEFAULT TRUE;

ALTER TABLE livraison
    ALTER COLUMN statut SET DEFAULT 'ENATTENTE';

ALTER TABLE vehicule
    ADD CONSTRAINT chk_capacite_positive CHECK (capacite > 0);