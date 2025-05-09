package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EquipesController {
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
    private ListView<InscriptionController.Team> teamsListView;

    @FXML
    private Button detailsButton;

    @FXML
    private Label teamNameLabel;

    @FXML
    private Label managerLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TableView<InscriptionController.Player> playersTableView;

    @FXML
    private TableColumn<InscriptionController.Player, String> lastNameColumn;

    @FXML
    private TableColumn<InscriptionController.Player, String> firstNameColumn;

    @FXML
    private TableColumn<InscriptionController.Player, String> emailColumn;

    // Exemple de liste d'équipes (à remplacer par votre implémentation)
    private List<InscriptionController.Team> teams = new ArrayList<>();

    @FXML
    private void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons de navigation
        setupNavigationButtons();

        // Configuration de la liste des équipes
        setupTeamsList();

        // Configuration du tableau des joueurs
        setupPlayersTable();

        // Configuration du bouton de détails
        detailsButton.setOnAction(event -> showTeamDetails());
    }

    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    private void setupTeamsList() {
        // Charger les équipes (à adapter selon votre implémentation)
        // Exemple : teams = getRegisteredTeams();

        ObservableList<InscriptionController.Team> teamsObservable = FXCollections.observableArrayList(teams);
        teamsListView.setItems(teamsObservable);

        // Configurer l'affichage des équipes dans la liste
        teamsListView.setCellFactory(param -> new ListCell<InscriptionController.Team>() {
            @Override
            protected void updateItem(InscriptionController.Team team, boolean empty) {
                super.updateItem(team, empty);
                if (empty || team == null) {
                    setText(null);
                } else {
                    setText(team.getName());
                }
            }
        });

        // Ajouter un écouteur de sélection
        teamsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTeamDetails(newValue));
    }

    private void setupPlayersTable() {
        // Configurer les colonnes du tableau
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void showTeamDetails() {
        InscriptionController.Team selectedTeam = teamsListView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            showTeamDetails(selectedTeam);
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une équipe pour voir ses détails.");
        }
    }

    private void showTeamDetails(InscriptionController.Team team) {
        if (team != null) {
            // Afficher les détails de l'équipe
            teamNameLabel.setText(team.getName());
            managerLabel.setText(team.getManager());
            emailLabel.setText(team.getEmail());
            phoneLabel.setText(team.getPhone());

            // Afficher les joueurs de l'équipe
            ObservableList<InscriptionController.Player> playersObservable =
                    FXCollections.observableArrayList(team.getPlayers());
            playersTableView.setItems(playersObservable);
        } else {
            // Effacer les détails
            teamNameLabel.setText("");
            managerLabel.setText("");
            emailLabel.setText("");
            phoneLabel.setText("");
            playersTableView.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void handleAccueilButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            Scene scene = accueilButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert("Erreur de navigation", "Impossible de charger la page d'accueil: " + e.getMessage());
        }
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
        // Nous sommes déjà sur la page des équipes, donc rien à faire
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
        showAlert("Aide", "Page des équipes\n\n" +
                "Cette page vous permet de consulter la liste des équipes inscrites au tournoi " +
                "et de voir les détails de chaque équipe, y compris la liste des joueurs.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
