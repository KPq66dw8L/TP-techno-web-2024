# The Donor Zone


## Fonctionnalités:

- site d'annonces
- une annonce pour faire un don contient: 
  - [ ] titre
  - [ ] description
  - [ ] état de l'objet
  - [ ] date de publication 
  - [ ] zone géographique
  - [ ] don main propre / envoyé
  - [ ] liste de mot-clés
- recherche parmi les annonces + filtrage
- une recherche peut etre enregistrée SI personne logged in
- envoie de notification si nouvelle annonce correspondante à une recherche enregistrée
- lots d'objets pour transaction groupée
- messagerie interne

## Technique: 

- serveur web en Spring Boot
- base de données MariaDB
- prise en compte du passage à l'échelle
- requetes conditionnelles
- entete de cache
- repartition de charge -> session sur la DB, gestion du cache etc (comme si on avait un traefik et de la replication de notre application dans le futur)
- interface web -> HTML rendu sur serveur
- il doit etre possible d'utiliser différents Front -> faire une API coté serveur pour couplage faible par négociation de contenu 
- tests unitaires et fonctionnels
- script d'exécution/compilation (gradle, bash, maven...)
- résumé du projet en PDF (architecture, modèle de données, schéma d'URL, pour chaque type de ressource la représentation)
- rendre une archive avec tout, en ZIP

## Outils

1. Serveur web
- Spring Boot
- Gradle
2. Base de données
  - H2
3. Frontend
   - Thymeleaf
4. API
- Spring Web
5. Recherche et filtrage
- Hibernate search ?
6. Messagerie
- Spring Messaging 
7. Tests
- JUnit -> unitaires
- Spring test -> fonctionnels
8. Cache
- Spring Cache ?
9. API Documentation
- Swagger/OpenAPI

1. Table users

   user_id (PK, INT, AUTO_INCREMENT)
   username (VARCHAR)
   email (VARCHAR)
   password_hash (VARCHAR)
   created_at (DATETIME)
   updated_at (DATETIME)

2. Table annonces

   annonce_id (PK, INT, AUTO_INCREMENT)
   user_id (FK, INT) — Lien vers l'utilisateur qui a créé l'annonce
   titre (VARCHAR)
   description (TEXT)
   etat (VARCHAR) — Ex: "Neuf", "Bon état", "À réparer"
   date_publication (DATETIME)
   zone_geographique (VARCHAR)
   don_type (ENUM) — Ex: "Main propre", "Envoyé"
   created_at (DATETIME)
   updated_at (DATETIME)

3. Table mots_cles

   mot_cle_id (PK, INT, AUTO_INCREMENT)
   mot_cle (VARCHAR) — Ex: "livre", "jouet", etc.

4. Table annonce_mots_cles

   annonce_id (FK, INT) — Lien vers l'annonce
   mot_cle_id (FK, INT) — Lien vers le mot-clé

5. Table recherches_enregistrees

   recherche_id (PK, INT, AUTO_INCREMENT)
   user_id (FK, INT) — Lien vers l'utilisateur
   titre (VARCHAR) — Critère de recherche
   date_enregistrement (DATETIME)
   filtres (JSON) — Critères de filtrage

6. Table notifications

   notification_id (PK, INT, AUTO_INCREMENT)
   user_id (FK, INT) — Lien vers l'utilisateur
   recherche_id (FK, INT) — Lien vers la recherche enregistrée
   annonce_id (FK, INT) — Lien vers l'annonce correspondante
   date_envoi (DATETIME)

7. Table lots

   lot_id (PK, INT, AUTO_INCREMENT)
   user_id (FK, INT) — Lien vers l'utilisateur qui a créé le lot
   date_creation (DATETIME)

8. Table lot_annonces

   lot_id (FK, INT) — Lien vers le lot
   annonce_id (FK, INT) — Lien vers l'annonce

9. Table messages

   message_id (PK, INT, AUTO_INCREMENT)
   sender_id (FK, INT) — Lien vers l'utilisateur qui envoie le message
   receiver_id (FK, INT) — Lien vers l'utilisateur qui reçoit le message
   content (TEXT)
   date_envoi (DATETIME)
   lu (BOOLEAN) — Statut de lecture