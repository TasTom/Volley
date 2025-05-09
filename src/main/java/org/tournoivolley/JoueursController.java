package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JoueursController {
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
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<PlayerWithTeam> playersTableView;

    @FXML
    private TableColumn<PlayerWithTeam, String> lastNameColumn;

    @FXML
    private TableColumn<PlayerWithTeam, String> firstNameColumn;

    @FXML
    private TableColumn<PlayerWithTeam, String> emailColumn;

    @FXML
    private TableColumn<PlayerWithTeam, String> teamColumn;

    @FXML
    private Label totalPlayersLabel;

    // Liste de tous les joueurs avec leur équipe
    private ObservableList<PlayerWithTeam> allPlayers = FXCollections.observableArrayList();

    // Liste filtrée des joueurs
    private FilteredList<PlayerWithTeam> filteredPlayers;

    // Classe interne pour représenter un joueur avec son équipe
    public static class PlayerWithTeam {
        private final InscriptionController.Player player;
        private final String teamName;

        public PlayerWithTeam(InscriptionController.Player player, String teamName) {
            this.player = player;
            this.teamName = teamName;
        }

        public String getFirstName() {
            return player.getFirstName();
        }

        public String getLastName() {
            return player.getLastName();
        }

        public String getEmail() {
            return player.getEmail();
        }

        public String getTeamName() {
            return teamName;
        }
    }

    @FXML
    private void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons de navigation
        setupNavigationButtons();

        // Configuration du tableau des joueurs
        setupPlayersTable();

        // Configuration de la recherche
        setupSearch();

        // Chargement des joueurs
        loadAllPlayers();
    }

    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    private void setupPlayersTable() {
        // Configurer les colonnes du tableau
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
    }

    private void setupSearch() {
        // Configurer la liste filtrée
        filteredPlayers = new FilteredList<>(allPlayers, p -> true);
        playersTableView.setItems(filteredPlayers);

        // Configurer les boutons de recherche
        searchButton.setOnAction(event -> {
            String searchText = searchField.getText().toLowerCase();
            filteredPlayers.setPredicate(player -> {
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }

                return player.getLastName().toLowerCase().contains(searchText) ||
                        player.getFirstName().toLowerCase().contains(searchText);
            });

            updateTotalPlayersLabel();
        });

        clearButton.setOnAction(event -> {
            searchField.clear();
            filteredPlayers.setPredicate(p -> true);
            updateTotalPlayersLabel();
        });
    }

    private void loadAllPlayers() {
        // Charger tous les joueurs de toutes les équipes (à adapter selon votre implémentation)
        // Exemple :
        // List<InscriptionController.Team> teams = getRegisteredTeams();
        List<InscriptionController.Team> teams = new ArrayList<>(); // À remplacer par la vraie liste

        allPlayers.clear();

        for (InscriptionController.Team team : teams) {
            String teamName = team.getName();
            for (InscriptionController.Player player : team.getPlayers()) {
                allPlayers.add(new PlayerWithTeam(player, teamName));
            }
        }

        updateTotalPlayersLabel();
    }

    private void updateTotalPlayersLabel() {
        totalPlayersLabel.setText("Total: " + filteredPlayers.size() + " joueurs");
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
        // Nous sommes déjà sur la page des joueurs, donc rien à faire
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
        showAlert("Aide", "Page des joueurs\n\n" +
                "Cette page vous permet de consulter la liste de tous les joueurs inscrits au tournoi. " +
                "Vous pouvez rechercher un joueur par son nom ou son prénom.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
