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


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.io.PrintWriter;


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
    private Button exportButton;

    @FXML
    private Button refreshButton;


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
        // Configuration du bouton d'exportation
        exportButton.setOnAction(event -> exportPlayersList());
        // Configuration du bouton de rafraîchissement
        refreshButton.setOnAction(event -> handleRefresh());

        // Configuration des statistiques des joueurs
        setupPlayerStats();
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
        // Récupérer toutes les équipes inscrites
        List<InscriptionController.Team> teams = InscriptionController.getRegisteredTeams();
        allPlayers.clear();

        // Parcourir chaque équipe et ajouter ses joueurs à la liste
        for (InscriptionController.Team team : teams) {
            String teamName = team.getName();
            for (InscriptionController.Player player : team.getPlayers()) {
                allPlayers.add(new PlayerWithTeam(player, teamName));
            }
        }

        // Mettre à jour le compteur de joueurs
        updateTotalPlayersLabel();
    }

    private void updateTotalPlayersLabel() {
        totalPlayersLabel.setText("Total: " + filteredPlayers.size() + " joueurs");
    }

    @FXML
    private void handleRefresh() {
        loadAllPlayers();
        searchField.clear();
        filteredPlayers.setPredicate(p -> true);
        showAlert(Alert.AlertType.INFORMATION, "Actualisation", "La liste des joueurs a été actualisée.");
    }

    @FXML
    private void handleAccueilButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            Scene scene = accueilButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page d'accueil: " + e.getMessage());
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
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page d'inscription: " + e.getMessage());
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
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page des équipes: " + e.getMessage());
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
            showAlert(Alert.AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page du planning: " + e.getMessage());
        }
    }

    @FXML
    private void handleAideButton() {
        // Afficher une boîte de dialogue d'aide
        showAlert(Alert.AlertType.ERROR, "Aide", "Page des joueurs\n\n" +
                "Cette page vous permet de consulter la liste de tous les joueurs inscrits au tournoi. " +
                "Vous pouvez rechercher un joueur par son nom ou son prénom.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void exportPlayersList() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la liste des joueurs");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        fileChooser.setInitialFileName("joueurs_tournoi.csv");

        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                // Écrire l'en-tête
                writer.println("Nom,Prénom,Email,Équipe");

                // Écrire les données
                for (PlayerWithTeam player : filteredPlayers) {
                    writer.println(
                            player.getLastName() + "," +
                                    player.getFirstName() + "," +
                                    player.getEmail() + "," +
                                    player.getTeamName()
                    );
                }

                showAlert(Alert.AlertType.INFORMATION, "Export réussi",
                        "La liste des joueurs a été exportée avec succès.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur d'export",
                        "Une erreur est survenue lors de l'export : " + e.getMessage());
            }
        }
    }

    private void setupPlayerStats() {
        // Ajouter un écouteur de sélection
        playersTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showPlayerDetails(newValue);
                    }
                });
    }

    private void showPlayerDetails(PlayerWithTeam player) {
        // Afficher les détails du joueur dans une boîte de dialogue
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Détails du joueur");
        dialog.setHeaderText(player.getFirstName() + " " + player.getLastName());

        // Créer le contenu
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Informations de base
        content.getChildren().add(new Label("Équipe: " + player.getTeamName()));
        content.getChildren().add(new Label("Email: " + player.getEmail()));

        // Générer des statistiques basées sur le nom du joueur (pour simulation)
        int matchesPlayed = player.getLastName().length() % 10 + 1;
        int points = player.getFirstName().length() * 5;
        int aces = player.getLastName().length() % 5;

        // Statistiques
        content.getChildren().add(new Separator());
        content.getChildren().add(new Label("Statistiques du tournoi:"));
        GridPane stats = new GridPane();
        stats.setHgap(10);
        stats.setVgap(5);
        stats.add(new Label("Matchs joués:"), 0, 0);
        stats.add(new Label(String.valueOf(matchesPlayed)), 1, 0);
        stats.add(new Label("Points marqués:"), 0, 1);
        stats.add(new Label(String.valueOf(points)), 1, 1);
        stats.add(new Label("Services gagnants:"), 0, 2);
        stats.add(new Label(String.valueOf(aces)), 1, 2);
        content.getChildren().add(stats);

        // Configurer la boîte de dialogue
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }


}
