package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.util.List;

public class AccueilController {
    @FXML
    private Button accueilButton;

    @FXML
    private Button inscriptionButton;

    @FXML
    private Button equipesButton;

    @FXML
    private Button joueursButton;

    @FXML
    private Button planningButton;

    @FXML
    private Button aideButton;

    @FXML
    private Button startInscriptionButton;

    @FXML
    private Label teamsCountLabel;

    @FXML
    private ProgressBar teamsProgressBar;

    @FXML
    private Label playersCountLabel;

    @FXML
    private Label nextMatchLabel;

    @FXML
    private Label nextMatchDateLabel;

    @FXML
    private ListView<String> newsListView;

    // Constante
    private static final int MAX_TEAMS = 8;


    @FXML
    private void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons de navigation
        setupNavigationButtons();

        // Mise à jour des informations sur les équipes
        updateTeamsInfo();

        setupDashboard();
        loadNews();

        // Configuration du bouton d'inscription
        startInscriptionButton.setOnAction(event -> handleStartInscription());
    }

    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    private void updateTeamsInfo() {
        // Récupérer le nombre d'équipes inscrites (à adapter selon votre implémentation)
        int registeredTeamsCount = 0; // À remplacer par la vraie valeur
        int maxTeams = 8;

        teamsCountLabel.setText(registeredTeamsCount + "/" + maxTeams + " équipes");
        teamsProgressBar.setProgress((double) registeredTeamsCount / maxTeams);
    }

    private void handleStartInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
            Parent root = loader.load();
            Scene scene = startInscriptionButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page d'inscription: " + e.getMessage());
        }
    }

    @FXML
    private void handleAccueilButton() {
        // Nous sommes déjà sur la page d'accueil, donc rien à faire
    }

    @FXML
    private void handleInscriptionButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
            Parent root = loader.load();
            Scene scene = inscriptionButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page d'inscription: " + e.getMessage());
        }
    }

    @FXML
    private void handleEquipesButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("equipes.fxml"));
            Parent root = loader.load();
            Scene scene = equipesButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page des équipes: " + e.getMessage());
        }
    }

    @FXML
    private void handleJoueursButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("joueurs.fxml"));
            Parent root = loader.load();
            Scene scene = joueursButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page des joueurs: " + e.getMessage());
        }
    }

    @FXML
    private void handlePlanningButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("planning.fxml"));
            Parent root = loader.load();
            Scene scene = planningButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page du planning: " + e.getMessage());
        }
    }

    @FXML
    private void handleAideButton() {
        // Afficher une boîte de dialogue d'aide
        showAlert("Aide", "Bienvenue dans l'application de gestion du tournoi de volley-ball.\n\n" +
                "Cette application vous permet de gérer les inscriptions des équipes, " +
                "consulter la liste des équipes et des joueurs, et visualiser le planning des matchs.");
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupDashboard() {
        // Récupérer les données
        List<InscriptionController.Team> teams = InscriptionController.getRegisteredTeams();
        int totalPlayers = 0;
        for (InscriptionController.Team team : teams) {
            totalPlayers += team.getPlayers().size();
        }

        // Mettre à jour les statistiques
        teamsCountLabel.setText(teams.size() + "/" + MAX_TEAMS + " équipes");
        teamsProgressBar.setProgress((double) teams.size() / MAX_TEAMS);
        playersCountLabel.setText(totalPlayers + " joueurs inscrits");

        // Afficher le prochain match s'il y en a
        if (teams.size() >= 2) {
            nextMatchLabel.setText(teams.get(0).getName() + " vs " + teams.get(1).getName());
            nextMatchDateLabel.setText("Samedi prochain à 14h00");
        } else {
            nextMatchLabel.setText("Aucun match programmé");
            nextMatchDateLabel.setText("Inscrivez au moins 2 équipes");
        }
    }
    private void loadNews() {
        // Simuler des actualités
        ObservableList<String> news = FXCollections.observableArrayList(
                "Ouverture des inscriptions pour le tournoi 2025",
                "Nouveau sponsor : SportEquipment rejoint l'aventure",
                "Les terrains du gymnase municipal ont été rénovés"
        );
        newsListView.setItems(news);
    }


}
