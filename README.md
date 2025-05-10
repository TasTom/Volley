# Application de gestion de tournoi de volley-ball

Cette application Java permet de gérer un tournoi de volley-ball prévu pour le 10 mai 2025. Développée avec JavaFX, elle offre une interface graphique complète pour l'organisation du tournoi, depuis l'inscription des équipes jusqu'à la génération du planning des matchs.

## Fonctionnalités

### Inscription des équipes
- Enregistrement des équipes avec leurs informations (nom, responsable, email, téléphone)
- Ajout de joueurs pour chaque équipe (6 joueurs requis par équipe)
- Validation des données saisies (email, téléphone, etc.)
- Limite de 8 équipes maximum pour le tournoi

### Gestion des équipes
- Visualisation de la liste des équipes inscrites
- Consultation des détails de chaque équipe
- Affichage des joueurs par équipe
- Statistiques de composition des équipes
- Recherche d'équipes par nom

### Gestion des joueurs
- Liste complète de tous les joueurs inscrits
- Recherche de joueurs par nom ou prénom
- Consultation des statistiques individuelles
- Exportation de la liste des joueurs au format CSV

### Planning des matchs
- Génération automatique du planning optimisé
- Organisation des matchs en phase de groupes et phase finale
- Gestion des terrains et des créneaux horaires
- Mise à jour des scores et calcul automatique du classement
- Exportation du planning en PDF (plusieurs formats disponibles)

## Structure de l'application

L'application est organisée en plusieurs onglets accessibles depuis la barre de navigation :

- **Accueil** : Tableau de bord avec les statistiques du tournoi et les actualités
- **Inscription** : Formulaire d'inscription des équipes et des joueurs
- **Équipes** : Gestion et visualisation des équipes inscrites
- **Joueurs** : Liste et recherche de tous les joueurs
- **Planning** : Génération et gestion du planning des matchs
- **Aide** : Assistance pour l'utilisation de l'application

## Prérequis techniques

- Java 11 ou supérieur
- JavaFX 11 ou supérieur
- Gradle pour la compilation et l'exécution

## Guide d'utilisation

### Inscription d'une équipe
1. Accédez à l'onglet "Inscription"
2. Remplissez les informations de l'équipe (nom, responsable, email, téléphone)
3. Ajoutez 6 joueurs à l'équipe en cliquant sur "Ajouter un joueur"
4. Cliquez sur "Inscrire l'équipe" pour finaliser l'inscription

### Consultation des équipes
1. Accédez à l'onglet "Équipes"
2. Sélectionnez une équipe dans la liste pour voir ses détails
3. Utilisez le champ de recherche pour filtrer les équipes
4. Cliquez sur "Actualiser" pour mettre à jour la liste des équipes

### Gestion des joueurs
1. Accédez à l'onglet "Joueurs"
2. Utilisez le champ de recherche pour trouver un joueur spécifique
3. Cliquez sur un joueur pour voir ses statistiques détaillées
4. Utilisez le bouton "Exporter" pour générer un fichier CSV de tous les joueurs

### Génération du planning
1. Accédez à l'onglet "Planning"
2. Cliquez sur "Générer le planning"
3. Consultez les matchs dans les onglets "Phase de groupes" et "Phase finale"
4. Mettez à jour les scores en double-cliquant sur la cellule correspondante
5. Exportez le planning en PDF en cliquant sur "Exporter en PDF"

## État actuel du projet

L'application est actuellement en phase de développement pour le tournoi qui aura lieu le 10 mai 2025. Les fonctionnalités principales sont opérationnelles, mais certaines améliorations sont encore en cours.

## Fonctionnalités à venir

- Ajout d'une fonctionnalité de persistance des données
- Intégration d'un système de notification pour les équipes
- Support pour différents formats de tournoi
- Interface responsive pour utilisation sur mobile
- Gestion des arbitres et des bénévoles

## Contribution

Les contributions au projet sont les bienvenues. Pour contribuer :

1. Forkez le dépôt
2. Créez une branche pour votre fonctionnalité (`git checkout -b nouvelle-fonctionnalite`)
3. Committez vos changements (`git commit -m 'Ajout d'une nouvelle fonctionnalité'`)
4. Poussez vers la branche (`git push origin nouvelle-fonctionnalite`)
5. Ouvrez une Pull Request

## Licence

Ce projet est distribué sous licence MIT. Voir le fichier LICENSE pour plus de détails.

## Contact

Pour toute question ou suggestion concernant l'application, veuillez contacter l'organisateur du tournoi à l'adresse tournoi@volleyball-club.fr