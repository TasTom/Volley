package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
    private Button refreshButton;


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
    private TextField filterField;

    @FXML
    private VBox teamStatsContainer;

    // Liste observable pour les équipes
    private ObservableList<InscriptionController.Team> teamsObservable;


    @FXML
    private void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons de navigation
        setupNavigationButtons();

        // Récupérer les équipes enregistrées
        teams = InscriptionController.getRegisteredTeams();
        teamsObservable = FXCollections.observableArrayList(teams);

        // Configuration de la liste des équipes
        setupTeamsList();

        // Configuration du tableau des joueurs
        setupPlayersTable();

        // Configuration du bouton de détails
        detailsButton.setOnAction(event -> showTeamDetails());

        // Configuration du filtre de recherche
        setupFilter();
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
        // Utiliser la liste observable déjà initialisée
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
            managerLabel.setText("Responsable: " + team.getManager());
            emailLabel.setText("Email: " + team.getEmail());
            phoneLabel.setText("Téléphone: " + team.getPhone());

            // Afficher les joueurs de l'équipe
            ObservableList<InscriptionController.Player> playersObservable =
                    FXCollections.observableArrayList(team.getPlayers());
            playersTableView.setItems(playersObservable);

            // Afficher les statistiques de l'équipe
            showTeamStats(team);
        } else {
            // Effacer les détails
            teamNameLabel.setText("");
            managerLabel.setText("");
            emailLabel.setText("");
            phoneLabel.setText("");
            playersTableView.setItems(FXCollections.observableArrayList());
            teamStatsContainer.getChildren().clear();
        }
    }

    @FXML
    private void handleRefresh() {
        // Recharger les équipes
        teams = InscriptionController.getRegisteredTeams();
        teamsObservable = FXCollections.observableArrayList(teams);
        teamsListView.setItems(teamsObservable);

        // Effacer les détails
        teamNameLabel.setText("");
        managerLabel.setText("");
        emailLabel.setText("");
        phoneLabel.setText("");
        playersTableView.setItems(FXCollections.observableArrayList());
        teamStatsContainer.getChildren().clear();

        // Afficher un message de confirmation
        showAlert("Actualisation", "La liste des équipes a été actualisée.");
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

    private void setupFilter() {
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                teamsListView.setItems(teamsObservable);
            } else {
                ObservableList<InscriptionController.Team> filteredList = FXCollections.observableArrayList();
                for (InscriptionController.Team team : teamsObservable) {
                    if (team.getName().toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(team);
                    }
                }
                teamsListView.setItems(filteredList);
            }
        });
    }



    private void showTeamStats(InscriptionController.Team team) {
        // Créer un graphique pour visualiser les statistiques
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Composition de l'équipe");

        // Calculer le nombre de joueurs par poste (exemple fictif basé sur les noms)
        int attaquants = 0;
        int passeurs = 0;
        int liberos = 0;

        for (InscriptionController.Player player : team.getPlayers()) {
            String name = player.getLastName().toLowerCase();
            if (name.length() % 3 == 0) {
                attaquants++;
            } else if (name.length() % 3 == 1) {
                passeurs++;
            } else {
                liberos++;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Attaquants", attaquants),
                new PieChart.Data("Passeurs", passeurs),
                new PieChart.Data("Libéros", liberos)
        );

        pieChart.setData(pieChartData);
        teamStatsContainer.getChildren().clear();
        teamStatsContainer.getChildren().add(pieChart);
    }



}
