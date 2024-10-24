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
3. Conteneurisation ?
- Docker 
- Docker compose
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

docker-compose up -build