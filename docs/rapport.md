# Documentation du Système



## 1. Architecture du Système

### Structure Globale

- **Modèle :** Utilisation du framework **Spring Boot** avec une architecture **MVC (Modèle-Vue-Contrôleur)**.

- **Modules Principaux :**

  - **Controller :** Gestion des endpoints REST.

  - **Service :** Logique métier et traitement des données.

  - **Repository :** Interface pour interagir avec la base de données en utilisant JPA/Hibernate.

  - **Model :** Représentation des entités persistantes et des DTO (Data Transfer Object).

  - **Security :** Gestion de l’authentification et des autorisations.



### Organisation du Code

- Arborescence :

  ```

  src/

  ├── main/

  │   ├── kotlin/

  │   │   └── com.thedonorzone.thedonorzone/

  │   │       ├── controller/  # Contient les contrôleurs REST

  │   │       ├── dto/         # Objets de transfert de données

  │   │       ├── model/       # Entités JPA

  │   │       ├── repository/  # Interfaces JpaRepository

  │   │       ├── service/     # Logique métier

  │   │       └── security/    # Configuration et gestion de la sécurité

  │   └── resources/           # Fichiers de configuration (application.yml)

  └── test/                    # Tests unitaires et d'intégration

  ```



### Technologies et Outils

- **Backend :** Spring Boot, Java 21.

- **Build Tool :** Gradle (Groovy DSL).

- **Base de Données :** PostgreSQL (via JPA/Hibernate).

- **Sécurité :** Spring Security avec JWT.

- **Tests :** JUnit 5 et MockMVC.



---



## 2. Modèle de Données

### Aperçu Général

- **Entités Principales :**

  - `User` : Représente les utilisateurs de l'application.

  - `Annoucement` : Représente les annonces publiées par les utilisateurs.

  - `Message` : Messages échangés entre utilisateurs.

  - `Favorites` : Gestion des annonces favorites des utilisateurs.



### Relations Entre Entités

- **User ↔ Annoucement :** Relation un-à-plusieurs (un utilisateur peut publier plusieurs annonces).

- **Annoucement ↔ Message :** Relation un-à-plusieurs (une annonce peut avoir plusieurs messages).

- **User ↔ Favorites :** Relation un-à-plusieurs (un utilisateur peut avoir plusieurs annonces favorites).



### Détail des Entités

#### User

```kotlin

@Entity

@Table(name = "users")

data class User(

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    val id: Long? = null,



    @Column(name = "username", unique = true, nullable = false)

    var username: String? = null,



    @Column(name = "email", unique = true, nullable = false)

    val email: String? = null,



    var password: String,



    @Column(name = "created_at")

    val createdAt: LocalDateTime = LocalDateTime.now(),



    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])

    val announcements: List<Announcement> = mutableListOf()

)

```



#### Annoucement

```kotlin

@Entity

@Table(name = "announcements")

data class Annoucement(

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    val id: Long? = null,



    val title: String,

    val description: String,

    val state: EnumState = EnumState.ACTIVE,



    @ManyToOne

    @JoinColumn(name = "user_id")

    val user: User? = null,



    @OneToMany(mappedBy = "announcement", cascade = [CascadeType.ALL])

    val messages: List<Message> = mutableListOf()

)

```



---



## 3. Ressources et Représentations

### Ressource : Utilisateurs (Users)

- **URL de base :** `/users`

- **Endpoints :**

  - `POST /users` : Création d’un utilisateur.

    - **Payload :**

      ```json

      {

        "username": "john_doe",

        "email": "john.doe@example.com",

        "password": "securepassword"

      }

      ```

  - `GET /users/{id}` : Récupérer un utilisateur par ID.

    - **Réponse :**

      ```json

      {

        "id": 1,

        "username": "john_doe",

        "email": "john.doe@example.com",

        "createdAt": "2024-12-10T10:30:00"

      }

      ```



### Ressource : Annonces (Announcements)

- **URL de base :** `/api/announcements`

- **Endpoints :**

  - `GET /api/announcements` : Récupérer toutes les annonces.

  - `GET /api/announcements/{id}` : Récupérer une annonce spécifique.

  - `POST /api/announcements` : Créer une nouvelle annonce.

    - **Payload :**

      ```json

      {

        "title": "Don de meubles",

        "description": "Canapé en bon état",

        "state": "ACTIVE"

      }

      ```

  - `PUT /api/announcements/{id}` : Mettre à jour une annonce.

  - `DELETE /api/announcements/{id}` : Supprimer une annonce.



### Ressource : Messages (Messages)

- **URL de base :** `/api/messages`

- **Endpoints :**

  - `POST /api/messages` : Envoi d’un message.

    - **Payload :**

      ```json

      {

        "announcementId": 1,

        "senderId": 2,

        "content": "Bonjour, cette annonce m'intéresse."

      }

      ```



---



## 4. Démarche Suivie

### Étape 1 : Analyse et Conception

- **Objectifs :** Identifier les entités principales, leurs relations et les fonctionnalités clés (CRUD, gestion des utilisateurs, échanges entre utilisateurs).

- **Diagrammes :** Conception UML des entités et du schéma relationnel.



### Étape 2 : Développement

1. **Modèle de Données :**

  - Implémentation des entités JPA.

  - Configuration des relations et des types d'attributs.

2. **Contrôleurs REST :**

  - Définition des endpoints pour chaque ressource.

  - Gestion des exceptions avec des réponses standardisées.

3. **Services :**

  - Implémentation de la logique métier.

  - Utilisation des repositories pour accéder aux données.

4. **Sécurité :**

  - Mise en place de l’authentification JWT.

  - Protection des endpoints REST.



### Étape 3 : Tests et Validation

- **Tests Unitaires :** Vérification de la logique métier.

- **Tests d’Intégration :** Validation des interactions entre les couches.

- **Tests API :** Vérification des endpoints avec Postman.



---



## 5. Schéma d’URL et Méthodes

```

/users               (POST, GET)

/users/{id}          (GET)

/api/announcements       (GET, POST)

/api/announcements/{id}  (GET, PUT, DELETE)

/api/messages            (POST)