CREATE TABLE client (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    email      VARCHAR(150)  NOT NULL UNIQUE,
    ville      VARCHAR(100)  NOT NULL,
    telephone  VARCHAR(20)   NOT NULL
);

CREATE TABLE vehicule (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    matricule  VARCHAR(20)   NOT NULL UNIQUE,
    type       VARCHAR(50)   NOT NULL,
    capacite   INT           NOT NULL,
    statut     ENUM('DISPONIBLE','EN_LIVRAISON','MAINTENANCE') NOT NULL
);


CREATE TABLE chauffeur (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    telephone   VARCHAR(20)  NOT NULL,
    permis_type VARCHAR(10)  NOT NULL,
    disponible  BOOLEAN      NOT NULL
);


CREATE TABLE livraison (
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_livraison       DATE         NOT NULL,
    adresse_depart       VARCHAR(255) NOT NULL,
    adresse_destination  VARCHAR(255) NOT NULL,
    statut               ENUM('ENATTENTE','ENCOURS','LIVREE') NOT NULL,
    client_id            BIGINT,
    chauffeur_id         BIGINT,
    vehicule_id          BIGINT
);