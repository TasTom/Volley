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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlanningController {
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
    private Button generatePlanningButton;

    @FXML
    private Button exportPlanningButton;

    // Tableaux pour la phase de groupes
    @FXML
    private TableView<Match> groupPhaseTableView;

    @FXML
    private TableColumn<Match, String> groupMatchDateColumn;

    @FXML
    private TableColumn<Match, String> groupMatchTeam1Column;

    @FXML
    private TableColumn<Match, String> groupMatchTeam2Column;

    @FXML
    private TableColumn<Match, String> groupMatchScoreColumn;

    @FXML
    private TableColumn<Match, String> groupMatchLocationColumn;

    // Tableaux pour la phase finale
    @FXML
    private TableView<Match> finalPhaseTableView;

    @FXML
    private TableColumn<Match, String> finalMatchDateColumn;

    @FXML
    private TableColumn<Match, String> finalMatchTeam1Column;

    @FXML
    private TableColumn<Match, String> finalMatchTeam2Column;

    @FXML
    private TableColumn<Match, String> finalMatchScoreColumn;

    @FXML
    private TableColumn<Match, String> finalMatchLocationColumn;

    @FXML
    private TableColumn<Match, String> finalMatchRoundColumn;

    // Tableau pour le classement
    @FXML
    private TableView<TeamRanking> rankingTableView;

    @FXML
    private TableColumn<TeamRanking, Integer> rankingPositionColumn;

    @FXML
    private TableColumn<TeamRanking, String> rankingTeamColumn;

    @FXML
    private TableColumn<TeamRanking, Integer> rankingPointsColumn;

    @FXML
    private TableColumn<TeamRanking, Integer> rankingWinsColumn;

    @FXML
    private TableColumn<TeamRanking, Integer> rankingLossesColumn;

    @FXML
    private TableColumn<TeamRanking, String> rankingSetsColumn;

    // Classe pour représenter un match
    public static class Match {
        private final LocalDateTime dateTime;
        private final String team1;
        private final String team2;
        private final String score;
        private final String location;
        private final String round;

        public Match(LocalDateTime dateTime, String team1, String team2, String score, String location, String round) {
            this.dateTime = dateTime;
            this.team1 = team1;
            this.team2 = team2;
            this.score = score;
            this.location = location;
            this.round = round;
        }

        public String getDateTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dateTime.format(formatter);
        }

        public String getTeam1() {
            return team1;
        }

        public String getTeam2() {
            return team2;
        }

        public String getScore() {
            return score;
        }

        public String getLocation() {
            return location;
        }

        public String getRound() {
            return round;
        }
    }

    // Classe pour représenter le classement d'une équipe
    public static class TeamRanking {
        private final int position;
        private final String teamName;
        private final int points;
        private final int wins;
        private final int losses;
        private final String sets;

        public TeamRanking(int position, String teamName, int points, int wins, int losses, String sets) {
            this.position = position;
            this.teamName = teamName;
            this.points = points;
            this.wins = wins;
            this.losses = losses;
            this.sets = sets;
        }

        public int getPosition() {
            return position;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getPoints() {
            return points;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public String getSets() {
            return sets;
        }
    }

    @FXML
    private void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons de navigation
        setupNavigationButtons();

        // Configuration des tableaux
        setupTables();

        // Configuration des boutons d'action
        setupActionButtons();
    }

    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    private void setupTables() {
        // Configuration du tableau de la phase de groupes
        groupMatchDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        groupMatchTeam1Column.setCellValueFactory(new PropertyValueFactory<>("team1"));
        groupMatchTeam2Column.setCellValueFactory(new PropertyValueFactory<>("team2"));
        groupMatchScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        groupMatchLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Configuration du tableau de la phase finale
        finalMatchDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        finalMatchTeam1Column.setCellValueFactory(new PropertyValueFactory<>("team1"));
        finalMatchTeam2Column.setCellValueFactory(new PropertyValueFactory<>("team2"));
        finalMatchScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        finalMatchLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        finalMatchRoundColumn.setCellValueFactory(new PropertyValueFactory<>("round"));

        // Configuration du tableau de classement
        rankingPositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        rankingTeamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        rankingPointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        rankingWinsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        rankingLossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        rankingSetsColumn.setCellValueFactory(new PropertyValueFactory<>("sets"));
    }

    private void setupActionButtons() {
        generatePlanningButton.setOnAction(event -> generatePlanning());
        exportPlanningButton.setOnAction(event -> exportPlanning());
    }

    private void generatePlanning() {
        // Vérifier si suffisamment d'équipes sont inscrites
        List<InscriptionController.Team> teams = new ArrayList<>(); // À remplacer par la vraie liste

        if (teams.size() < 4) {
            showAlert("Génération impossible",
                    "Il faut au moins 4 équipes inscrites pour générer un planning. " +
                            "Actuellement, il y a " + teams.size() + " équipe(s) inscrite(s).");
            return;
        }

        // Générer les matchs de la phase de groupes (exemple)
        ObservableList<Match> groupMatches = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < teams.size() - 1; i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                LocalDateTime matchTime = now.plusDays(i).plusHours(j);
                groupMatches.add(new Match(
                        matchTime,
                        teams.get(i).getName(),
                        teams.get(j).getName(),
                        "0-0",
                        "Terrain " + ((i + j) % 3 + 1),
                        "Groupe A"
                ));
            }
        }

        groupPhaseTableView.setItems(groupMatches);

        // Générer les matchs de la phase finale (exemple)
        ObservableList<Match> finalMatches = FXCollections.observableArrayList();
        finalMatches.add(new Match(
                now.plusDays(7),
                "À déterminer",
                "À déterminer",
                "-",
                "Terrain 1",
                "Demi-finale 1"
        ));
        finalMatches.add(new Match(
                now.plusDays(7).plusHours(2),
                "À déterminer",
                "À déterminer",
                "-",
                "Terrain 1",
                "Demi-finale 2"
        ));
        finalMatches.add(new Match(
                now.plusDays(8),
                "À déterminer",
                "À déterminer",
                "-",
                "Terrain 1",
                "Finale"
        ));

        finalPhaseTableView.setItems(finalMatches);

        // Générer le classement (exemple)
        ObservableList<TeamRanking> rankings = FXCollections.observableArrayList();
        for (int i = 0; i < teams.size(); i++) {
            rankings.add(new TeamRanking(
                    i + 1,
                    teams.get(i).getName(),
                    0,
                    0,
                    0,
                    "0-0"
            ));
        }

        rankingTableView.setItems(rankings);

        showAlert("Planning généré", "Le planning des matchs a été généré avec succès.");
    }

    private void exportPlanning() {
        showAlert("Export en cours", "Fonctionnalité d'export en PDF non implémentée.");
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
        // Nous sommes déjà sur la page du planning, donc rien à faire
    }

    @FXML
    private void handleAideButton() {
        // Afficher une boîte de dialogue d'aide
        showAlert("Aide", "Page du planning\n\n" +
                "Cette page vous permet de générer et de consulter le planning des matchs du tournoi. " +
                "Vous pouvez voir les matchs de la phase de groupes, de la phase finale, ainsi que le classement des équipes.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
