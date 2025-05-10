package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.cell.TextFieldTableCell;
import java.util.HashMap;
import java.util.Map;



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

    @FXML
    private Button refreshButton;
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

        public LocalDateTime getDateTimeObject() {
            return dateTime;
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
        // Configuration de l'édition des scores
        setupScoreEditing();
    }


    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    private void setupActionButtons() {
        generatePlanningButton.setOnAction(event -> generatePlanning());
        exportPlanningButton.setOnAction(event -> exportPlanning());
        refreshButton.setOnAction(event -> refreshPlanning());
    }

    private void refreshPlanning() {
        // Vider les tableaux
        if (groupPhaseTableView.getItems() != null) {
            groupPhaseTableView.getItems().clear();
        }

        if (finalPhaseTableView.getItems() != null) {
            finalPhaseTableView.getItems().clear();
        }

        if (rankingTableView.getItems() != null) {
            rankingTableView.getItems().clear();
        }

        // Réinitialiser les tableaux avec des listes vides
        groupPhaseTableView.setItems(FXCollections.observableArrayList());
        finalPhaseTableView.setItems(FXCollections.observableArrayList());
        rankingTableView.setItems(FXCollections.observableArrayList());

        // Afficher un message
        showAlert(AlertType.INFORMATION, "Rafraîchissement",
                "Les données du planning ont été réinitialisées. Vous pouvez générer un nouveau planning pour le tournoi du 10 mai 2025.");
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


    @FXML
    private void generatePlanning() {
        // Vérifier si suffisamment d'équipes sont inscrites
        List<InscriptionController.Team> teams = InscriptionController.getRegisteredTeams();
        if (teams.size() < 4) {
            showAlert(AlertType.WARNING, "Génération impossible",
                    "Il faut au moins 4 équipes inscrites pour générer un planning. " +
                            "Actuellement, il y a " + teams.size() + " équipe(s) inscrite(s).");
            return;
        }

        // Utiliser l'algorithme optimisé pour générer le planning
        generateOptimizedSchedule(teams);

        // Générer les matchs de la phase finale
        generateFinalPhase(teams);

        // Générer le classement initial
        generateInitialRankings(teams);

        showAlert(AlertType.INFORMATION, "Planning généré",
                "Le planning des matchs a été généré avec succès pour le tournoi du 10 mai 2025.");
    }

    private void generateFinalPhase(List<InscriptionController.Team> teams) {
        // Date du tournoi: 10 mai 2025
        LocalDateTime tournamentDate = LocalDateTime.of(2025, 5, 10, 14, 0);

        ObservableList<Match> finalMatches = FXCollections.observableArrayList();

        // Demi-finales
        finalMatches.add(new Match(
                tournamentDate.withHour(14),
                "Vainqueur Groupe A",
                "Second Groupe B",
                "-",
                "Terrain 1",
                "Demi-finale 1"
        ));

        finalMatches.add(new Match(
                tournamentDate.withHour(15).withMinute(30),
                "Vainqueur Groupe B",
                "Second Groupe A",
                "-",
                "Terrain 1",
                "Demi-finale 2"
        ));

        // Petite finale (3e place)
        finalMatches.add(new Match(
                tournamentDate.withHour(17),
                "Perdant Demi-finale 1",
                "Perdant Demi-finale 2",
                "-",
                "Terrain 2",
                "Petite Finale"
        ));

        // Finale
        finalMatches.add(new Match(
                tournamentDate.withHour(18),
                "Vainqueur Demi-finale 1",
                "Vainqueur Demi-finale 2",
                "-",
                "Terrain 1",
                "Finale"
        ));

        finalPhaseTableView.setItems(finalMatches);
    }

    private void generateInitialRankings(List<InscriptionController.Team> teams) {
        ObservableList<TeamRanking> rankings = FXCollections.observableArrayList();

        // Créer un classement initial pour chaque équipe
        for (int i = 0; i < teams.size(); i++) {
            rankings.add(new TeamRanking(
                    i + 1,
                    teams.get(i).getName(),
                    0,  // points
                    0,  // victoires
                    0,  // défaites
                    "0-0"  // sets
            ));
        }

        rankingTableView.setItems(rankings);
    }



    @FXML
    private void handleAccueilButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            Scene scene = accueilButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert(AlertType.ERROR,"Erreur de navigation", "Impossible de charger la page d'accueil: " + e.getMessage());
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
            showAlert(AlertType.ERROR,"Erreur de navigation", "Impossible de charger la page d'inscription: " + e.getMessage());
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
            showAlert(AlertType.ERROR,"Erreur de navigation", "Impossible de charger la page des équipes: " + e.getMessage());
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
            showAlert(AlertType.ERROR,"Erreur de navigation", "Impossible de charger la page des joueurs: " + e.getMessage());
        }
    }

    @FXML
    private void handlePlanningButton() {
        // Nous sommes déjà sur la page du planning, donc rien à faire
    }

    @FXML
    private void handleAideButton() {
        // Afficher une boîte de dialogue d'aide
        showAlert(AlertType.INFORMATION,"Aide", "Page du planning\n\n" +
                "Cette page vous permet de générer et de consulter le planning des matchs du tournoi. " +
                "Vous pouvez voir les matchs de la phase de groupes, de la phase finale, ainsi que le classement des équipes.");
    }


    private void exportToPDF(Node contentNode) {
        try {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                // Afficher la boîte de dialogue d'impression
                boolean showDialog = job.showPrintDialog(contentNode.getScene().getWindow());

                if (showDialog) {
                    // Configurer la mise en page
                    PageLayout pageLayout = job.getPrinter().createPageLayout(
                            Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                    job.getJobSettings().setPageLayout(pageLayout);

                    // Imprimer le nœud
                    boolean success = job.printPage(contentNode);

                    if (success) {
                        job.endJob();
                        showAlert(AlertType.INFORMATION, "Export réussi",
                                "Le document a été exporté avec succès en PDF.");
                    } else {
                        showAlert(AlertType.ERROR, "Erreur d'export",
                                "L'export en PDF a échoué.");
                    }
                }
            } else {
                showAlert(AlertType.ERROR, "Erreur d'impression",
                        "Impossible de créer une tâche d'impression.");
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Erreur d'export",
                    "Une erreur est survenue lors de l'export : " + e.getMessage());
        }
    }



    @FXML
    private void exportPlanning() {
        // Créer un sélecteur de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le planning en PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        fileChooser.setInitialFileName("planning_tournoi_volley_10mai2025.pdf");

        // Afficher le sélecteur de fichier
        File file = fileChooser.showSaveDialog(planningButton.getScene().getWindow());

        if (file != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Type d'export");
            alert.setHeaderText("Que souhaitez-vous exporter ?");
            alert.setContentText("Choisissez le type d'export que vous souhaitez effectuer.");

            ButtonType buttonTypeOne = new ButtonType("Onglet actuel uniquement");
            ButtonType buttonTypeTwo = new ButtonType("Planning complet");
            ButtonType buttonTypeThree = new ButtonType("Planning avec en-tête personnalisé");

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

            alert.showAndWait().ifPresent(type -> {
                if (type == buttonTypeOne) {
                    // Exporter l'onglet actuel
                    TabPane tabPane = (TabPane) groupPhaseTableView.getParent().getParent();
                    Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                    Node contentToExport;

                    if (selectedTab.getText().equals("Phase de groupes")) {
                        contentToExport = groupPhaseTableView;
                    } else if (selectedTab.getText().equals("Phase finale")) {
                        contentToExport = finalPhaseTableView;
                    } else {
                        contentToExport = rankingTableView;
                    }

                    exportToPDF(contentToExport);
                } else if (type == buttonTypeTwo) {
                    // Exporter le planning complet
                    exportMultiPageToPDF();
                } else {
                    // Exporter avec en-tête personnalisé
                    exportCustomHeaderToPDF();
                }
            });
        }
    }



    private void exportMultiPageToPDF() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(planningButton.getScene().getWindow())) {
            // Configurer la mise en page
            PageLayout pageLayout = job.getPrinter().createPageLayout(
                    Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            boolean success = true;

            // Imprimer chaque tableau sur une page différente
            success &= job.printPage(groupPhaseTableView);
            success &= job.printPage(finalPhaseTableView);
            success &= job.printPage(rankingTableView);

            if (success) {
                job.endJob();
                showAlert(AlertType.INFORMATION, "Export réussi",
                        "Le planning complet a été exporté avec succès en PDF.");
            } else {
                showAlert(AlertType.ERROR, "Erreur d'export",
                        "L'export en PDF a échoué.");
            }
        }
    }

    private void exportCustomHeaderToPDF() {
        try {
            // Créer un sélecteur de fichier pour l'emplacement du PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le planning avec en-tête personnalisé");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
            fileChooser.setInitialFileName("planning_tournoi_volley_10mai2025_custom.pdf");

            File file = fileChooser.showSaveDialog(planningButton.getScene().getWindow());
            if (file == null) return;

            PrinterJob job = PrinterJob.createPrinterJob();
            if (job == null) {
                showAlert(AlertType.ERROR, "Erreur d'impression",
                        "Impossible de créer une tâche d'impression.");
                return;
            }

            // Configurer la mise en page
            PageLayout pageLayout = job.getPrinter().createPageLayout(
                    Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);

            boolean success = true;

            // Phase de groupes avec en-tête
            VBox groupPhaseWithHeader = new VBox(20);
            groupPhaseWithHeader.getChildren().add(createHeader());
            Label groupLabel = new Label("Phase de Groupes");
            groupLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            groupPhaseWithHeader.getChildren().add(groupLabel);
            groupPhaseWithHeader.getChildren().add(groupPhaseTableView);
            success &= job.printPage(groupPhaseWithHeader);

            // Phase finale avec en-tête
            VBox finalPhaseWithHeader = new VBox(20);
            finalPhaseWithHeader.getChildren().add(createHeader());
            Label finalLabel = new Label("Phase Finale");
            finalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            finalPhaseWithHeader.getChildren().add(finalLabel);
            finalPhaseWithHeader.getChildren().add(finalPhaseTableView);
            success &= job.printPage(finalPhaseWithHeader);

            // Classement avec en-tête
            VBox rankingWithHeader = new VBox(20);
            rankingWithHeader.getChildren().add(createHeader());
            Label rankingLabel = new Label("Classement");
            rankingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            rankingWithHeader.getChildren().add(rankingLabel);
            rankingWithHeader.getChildren().add(rankingTableView);
            success &= job.printPage(rankingWithHeader);

            if (success) {
                job.endJob();
                showAlert(AlertType.INFORMATION, "Export réussi",
                        "Le planning avec en-tête personnalisé a été exporté avec succès en PDF.");
            } else {
                showAlert(AlertType.ERROR, "Erreur d'export",
                        "L'export en PDF a échoué.");
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Erreur d'export",
                    "Une erreur est survenue lors de l'export : " + e.getMessage());
        }
    }

    // Méthode auxiliaire pour créer un en-tête cohérent
    private VBox createHeader() {
        VBox headerBox = new VBox(10);
        headerBox.setStyle("-fx-background-color: #E1F5FE; -fx-padding: 15;");

        Label titleLabel = new Label("Tournoi de Volley-ball 2025");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label dateLabel = new Label("Samedi 10 mai 2025 - Gymnase Municipal");
        dateLabel.setStyle("-fx-font-size: 14px;");

        Label organizerLabel = new Label("Organisé par: Club de Volley-ball Municipal");
        organizerLabel.setStyle("-fx-font-size: 12px;");

        headerBox.getChildren().addAll(titleLabel, dateLabel, organizerLabel);
        return headerBox;
    }





    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupScoreEditing() {
        // Rendre la colonne de score éditable pour la phase de groupes
        groupMatchScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        groupMatchScoreColumn.setOnEditCommit(event -> {
            Match match = event.getRowValue();
            String newScore = event.getNewValue();

            // Vérifier le format du score (par exemple: "25-20")
            if (newScore.matches("\\d+-\\d+")) {
                // Mettre à jour le score
                ObservableList<Match> matches = groupPhaseTableView.getItems();
                int index = matches.indexOf(match);

                // Créer un nouveau match avec le score mis à jour
                Match updatedMatch = new Match(
                        match.getDateTimeObject(), // Utilisez cette méthode au lieu de getDateTime()
                        match.getTeam1(),
                        match.getTeam2(),
                        newScore,
                        match.getLocation(),
                        match.getRound()
                );


                matches.set(index, updatedMatch);

                // Mettre à jour le classement
                updateRankings();
            } else {
                showAlert(AlertType.WARNING, "Format invalide",
                        "Le format du score doit être 'xx-xx' (ex: 25-20).");
                groupPhaseTableView.refresh();
            }
        });

        // Faire de même pour la phase finale
        finalMatchScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        finalMatchScoreColumn.setOnEditCommit(event -> {
            // Code similaire pour la phase finale
        });
    }



    private void updateRankings() {
        // Recalculer le classement en fonction des scores
        ObservableList<Match> matches = groupPhaseTableView.getItems();
        Map<String, TeamStats> teamStatsMap = new HashMap<>();

        // Parcourir tous les matchs pour calculer les statistiques
        for (Match match : matches) {
            String score = match.getScore();
            if (score.matches("\\d+-\\d+")) {
                String[] scores = score.split("-");
                int score1 = Integer.parseInt(scores[0]);
                int score2 = Integer.parseInt(scores[1]);

                // Mettre à jour les statistiques de l'équipe 1
                TeamStats stats1 = teamStatsMap.getOrDefault(match.getTeam1(), new TeamStats(match.getTeam1()));
                if (score1 > score2) {
                    stats1.addWin();
                } else {
                    stats1.addLoss();
                }
                stats1.addSets(score1, score2);
                teamStatsMap.put(match.getTeam1(), stats1);

                // Mettre à jour les statistiques de l'équipe 2
                TeamStats stats2 = teamStatsMap.getOrDefault(match.getTeam2(), new TeamStats(match.getTeam2()));
                if (score2 > score1) {
                    stats2.addWin();
                } else {
                    stats2.addLoss();
                }
                stats2.addSets(score2, score1);
                teamStatsMap.put(match.getTeam2(), stats2);
            }
        }

        // Convertir en liste et trier
        List<TeamStats> teamStatsList = new ArrayList<>(teamStatsMap.values());
        teamStatsList.sort((a, b) -> b.getPoints() - a.getPoints());

        // Mettre à jour le tableau de classement
        ObservableList<TeamRanking> rankings = FXCollections.observableArrayList();
        for (int i = 0; i < teamStatsList.size(); i++) {
            TeamStats stats = teamStatsList.get(i);
            rankings.add(new TeamRanking(
                    i + 1,
                    stats.getTeamName(),
                    stats.getPoints(),
                    stats.getWins(),
                    stats.getLosses(),
                    stats.getSetsFor() + "-" + stats.getSetsAgainst()
            ));
        }

        rankingTableView.setItems(rankings);
    }

    // Classe pour suivre les statistiques d'une équipe
    private static class TeamStats {
        private final String teamName;
        private int wins = 0;
        private int losses = 0;
        private int setsFor = 0;
        private int setsAgainst = 0;

        public TeamStats(String teamName) {
            this.teamName = teamName;
        }

        public void addWin() {
            wins++;
        }

        public void addLoss() {
            losses++;
        }

        public void addSets(int for_, int against) {
            setsFor += for_;
            setsAgainst += against;
        }

        public int getPoints() {
            return wins * 3 + losses * 0;
        }

        public String getTeamName() {
            return teamName;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getSetsFor() {
            return setsFor;
        }

        public int getSetsAgainst() {
            return setsAgainst;
        }
    }

    private void generateOptimizedSchedule(List<InscriptionController.Team> teams) {
        // Déterminer le nombre de terrains disponibles
        int courts = 3;

        // Déterminer les créneaux horaires disponibles
        LocalDateTime startDate = LocalDateTime.now().plusDays(7);
        List<LocalDateTime> timeSlots = new ArrayList<>();

        // Générer des créneaux sur deux jours
        for (int day = 0; day < 2; day++) {
            LocalDateTime dayStart = startDate.plusDays(day);
            for (int hour = 9; hour <= 17; hour += 2) {
                timeSlots.add(dayStart.withHour(hour).withMinute(0));
            }
        }

        // Générer toutes les combinaisons de matchs possibles
        List<Match> allMatches = new ArrayList<>();
        for (int i = 0; i < teams.size() - 1; i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                allMatches.add(new Match(
                        null, // Date à déterminer
                        teams.get(i).getName(),
                        teams.get(j).getName(),
                        "0-0",
                        null, // Terrain à déterminer
                        "Groupe A"
                ));
            }
        }

        // Algorithme d'optimisation pour attribuer les créneaux et les terrains
        ObservableList<Match> scheduledMatches = FXCollections.observableArrayList();
        int matchIndex = 0;

        for (LocalDateTime slot : timeSlots) {
            for (int court = 1; court <= courts && matchIndex < allMatches.size(); court++) {
                Match match = allMatches.get(matchIndex);

                // Créer un nouveau match avec le créneau et le terrain attribués
                Match scheduledMatch = new Match(
                        slot,
                        match.getTeam1(),
                        match.getTeam2(),
                        "0-0",
                        "Terrain " + court,
                        "Groupe A"
                );

                scheduledMatches.add(scheduledMatch);
                matchIndex++;
            }
        }

        groupPhaseTableView.setItems(scheduledMatches);
    }




}
