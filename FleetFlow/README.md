FleetFlow — API



FleetFlow est une API REST développée avec Spring Boot pour la gestion d'une flotte de livraison. Elle permet de gérer les clients, les chauffeurs, les véhicules et les livraisons, avec un système d'authentification sécurisé par JWT et une gestion fine des rôles utilisateurs.





&#x20;Fonctionnalités



* Authentification JWT : inscription, connexion, génération et validation de tokens
* Gestion des rôles : ADMIN, MANAGER, CHAUFFEUR avec permissions différenciées
* CRUD complet sur les entités : Clients, Chauffeurs, Véhicules, Livraisons
* Pagination et tri sur toutes les listes via paramètres URL
* Migrations de base de données automatiques avec Flyway
* Documentation API interactive via Swagger UI
* Conteneurisation Docker avec Docker Compose
* Pipeline CI/CD avec GitHub Actions





&#x20;Architecture du projet



src/

├── main/

│   ├── java/com/fleetflow/

│   │   ├── Controller/        # Couche REST (endpoints HTTP)

│   │   │   ├── AuthController.java

│   │   │   ├── ClientController.java

│   │   │   ├── ChauffeurController.java

│   │   │   ├── VehiculeController.java

│   │   │   ├── LivraisonController.java

│   │   │   └── UserController.java

│   │   ├── Service/           # Interfaces des services métier

│   │   │   └── impl/          # Implémentations concrètes

│   │   ├── Repository/        # Accès données (Spring Data JPA)

│   │   ├── Entity/            # Entités JPA (tables DB)

│   │   ├── Dto/               # Objets de transfert de données

│   │   ├── Mapper/            # MapStruct : Entity ↔ DTO

│   │   ├── security/          # JWT Filter, JwtUtils, SecurityConfig

│   │   ├── enums/             # Role, StatutLivraison, StatutVehicule

│   │   └── exception/         # Gestionnaire global des erreurs

│   └── resources/

│       ├── application.properties

│       └── db/migration/      # Scripts Flyway (V1, V2, V3)

└── test/

&#x20;   ├── java/com/fleetflow/

&#x20;   │   └── Service/           # Tests unitaires avec Mockito

&#x20;   └── resources/

&#x20;       └── application-test.properties  # Profil H2 pour les tests

