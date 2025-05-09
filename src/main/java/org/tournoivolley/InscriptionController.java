package org.tournoivolley;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InscriptionController {

    @FXML
    private TextField teamNameField;

    @FXML
    private TextField managerField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label playerCountLabel;

    @FXML
    private ListView<Player> playersListView;

    // Nouveaux contrôles pour mettre à jour l'affichage des équipes
    @FXML
    private VBox teamsList1;

    @FXML
    private VBox teamsList2;

    @FXML
    private javafx.scene.control.ProgressBar teamsProgressBar;

    @FXML
    private Label teamsCountLabel;

    @FXML
    private Label team1Label;

    @FXML
    private Label team2Label;
    @FXML
    private Label team3Label;
    @FXML
    private Label team4Label;
    @FXML
    private Label team5Label;
    @FXML
    private Label team6Label;
    @FXML
    private Label team7Label;
    @FXML
    private Label team8Label;

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


    // Liste observable pour stocker les joueurs de l'équipe en cours
    private ObservableList<Player> players = FXCollections.observableArrayList();

    // Liste pour stocker toutes les équipes inscrites
    private static List<Team> registeredTeams = new ArrayList<>();

    // Constantes
    private static final int MAX_TEAMS = 8;
    private static final int REQUIRED_PLAYERS = 6;

    @FXML
    private void initialize() {
        // Initialisation du contrôleur
        playersListView.setItems(players);

        playersListView.setCellFactory(lv -> new javafx.scene.control.ListCell<Player>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                } else {
                    setText(player.getLastName() + " " + player.getFirstName());
                }
            }
        });

        updatePlayerCount();
        updateTeamsDisplay();
        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        accueilButton.setOnAction(event -> handleAccueilButton());
        inscriptionButton.setOnAction(event -> handleInscriptionButton());
        equipesButton.setOnAction(event -> handleEquipesButton());
        joueursButton.setOnAction(event -> handleJoueursButton());
        planningButton.setOnAction(event -> handlePlanningButton());
        aideButton.setOnAction(event -> handleAideButton());
    }

    @FXML
    private void handleAccueilButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accueil.fxml"));
            Parent root = loader.load();
            Scene scene = accueilButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur de navigation",
                    "Impossible de charger la page d'accueil: " + e.getMessage());
        }
    }

    @FXML
    private void handleInscriptionButton() {
        // Nous sommes déjà sur la page d'inscription, donc rien à faire
        // Ou rafraîchir la page si nécessaire
        resetForm();
        updateTeamsDisplay();
    }

    @FXML
    private void handleEquipesButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("equipes.fxml"));
            Parent root = loader.load();
            Scene scene = equipesButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur de navigation",
                    "Impossible de charger la page des équipes: " + e.getMessage());
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
            showAlert(AlertType.ERROR, "Erreur de navigation",
                    "Impossible de charger la page des joueurs: " + e.getMessage());
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
            showAlert(AlertType.ERROR, "Erreur de navigation",
                    "Impossible de charger la page du planning: " + e.getMessage());
        }
    }

    @FXML
    private void handleAideButton() {
        // Afficher une boîte de dialogue d'aide
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aide");
        alert.setHeaderText("Aide pour l'inscription des équipes");
        alert.setContentText("Cette page vous permet d'inscrire une équipe au tournoi de volley-ball.\n\n" +
                "1. Remplissez les informations de l'équipe (nom, responsable, etc.)\n" +
                "2. Ajoutez au moins " + REQUIRED_PLAYERS + " joueurs à l'équipe\n" +
                "3. Cliquez sur 'Inscrire l'équipe' pour finaliser l'inscription\n\n" +
                "Pour toute assistance supplémentaire, contactez l'organisateur du tournoi.");
        alert.showAndWait();
    }



    @FXML
    private void handleAddPlayer() {
        try {
            // Vérifier si l'équipe a déjà le nombre maximal de joueurs
            if (players.size() >= REQUIRED_PLAYERS) {
                showAlert(AlertType.WARNING, "Limite atteinte",
                        "Une équipe ne peut pas avoir plus de " + REQUIRED_PLAYERS + " joueurs.");
                return;
            }

            // Ouvrir une boîte de dialogue pour ajouter un joueur
            Player newPlayer = showAddPlayerDialog();

            if (newPlayer != null) {
                // Ajouter le joueur à la liste
                players.add(newPlayer);
                updatePlayerCount();
            }

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur",
                    "Une erreur est survenue lors de l'ajout d'un joueur: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditPlayer() {
        try {
            // Récupérer le joueur sélectionné
            Player selectedPlayer = playersListView.getSelectionModel().getSelectedItem();

            if (selectedPlayer == null) {
                showAlert(AlertType.WARNING, "Aucune sélection",
                        "Veuillez sélectionner un joueur à modifier.");
                return;
            }

            // Ouvrir une boîte de dialogue pour modifier le joueur
            Player editedPlayer = showEditPlayerDialog(selectedPlayer);

            if (editedPlayer != null) {
                // Remplacer le joueur dans la liste
                int index = players.indexOf(selectedPlayer);
                players.set(index, editedPlayer);
            }

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur",
                    "Une erreur est survenue lors de la modification du joueur: " + e.getMessage());
        }
    }

    @FXML
    private void handleRemovePlayer() {
        // Récupérer le joueur sélectionné
        Player selectedPlayer = playersListView.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            showAlert(AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner un joueur à supprimer.");
            return;
        }

        // Supprimer le joueur de la liste
        players.remove(selectedPlayer);
        updatePlayerCount();
    }

    @FXML
    private void handleSubmit() {
        try {
            // Validation des champs
            if (teamNameField.getText() == null || teamNameField.getText().trim().isEmpty()) {
                throw new ValidationException("Le nom de l'équipe est obligatoire.");
            }

            if (managerField.getText() == null || managerField.getText().trim().isEmpty()) {
                throw new ValidationException("Le nom du responsable est obligatoire.");
            }

            if (emailField.getText() == null || emailField.getText().trim().isEmpty() || !isValidEmail(emailField.getText())) {
                throw new ValidationException("Veuillez fournir une adresse email valide.");
            }

            if (phoneField.getText() == null || phoneField.getText().trim().isEmpty() || !isValidPhone(phoneField.getText())) {
                throw new ValidationException("Veuillez fournir un numéro de téléphone valide.");
            }

            // Vérifier le nombre de joueurs
            if (players.size() < REQUIRED_PLAYERS) {
                throw new ValidationException("Vous devez ajouter exactement " + REQUIRED_PLAYERS + " joueurs.");
            }

            // Vérifier si le nom d'équipe est déjà utilisé
            String teamName = teamNameField.getText().trim();
            for (Team team : registeredTeams) {
                if (team.getName().equalsIgnoreCase(teamName)) {
                    throw new DuplicateTeamException("Ce nom d'équipe est déjà utilisé.");
                }
            }

            // Vérifier si le nombre maximum d'équipes est atteint
            if (registeredTeams.size() >= MAX_TEAMS) {
                throw new MaxTeamsReachedException("Le nombre maximum d'équipes (" + MAX_TEAMS + ") est atteint.");
            }

            // Créer et ajouter l'équipe
            Team newTeam = new Team(
                    teamNameField.getText().trim(),
                    managerField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    new ArrayList<>(players) // Copie des joueurs
            );

            registeredTeams.add(newTeam);

            // Mettre à jour l'affichage des équipes
            updateTeamsDisplay();

            // Afficher une confirmation
            showAlert(AlertType.INFORMATION, "Inscription réussie",
                    "L'équipe '" + newTeam.getName() + "' a été inscrite avec succès!");

            // Réinitialiser le formulaire
            resetForm();

        } catch (ValidationException | DuplicateTeamException | MaxTeamsReachedException e) {
            showAlert(AlertType.ERROR, "Erreur d'inscription", e.getMessage());
        }
    }


    private void updateTeamsDisplay() {
        // 1. Mettre à jour la barre de progression
        teamsProgressBar.setProgress((double) registeredTeams.size() / MAX_TEAMS);

        // 2. Mettre à jour le label du nombre d'équipes
        teamsCountLabel.setText(registeredTeams.size() + "/" + MAX_TEAMS + " équipes");

        // 3. Mettre à jour les labels des équipes
        Label[] teamLabels = {team1Label, team2Label, team3Label, team4Label,
                team5Label, team6Label, team7Label, team8Label};

        for (int i = 0; i < MAX_TEAMS; i++) {
            if (i < registeredTeams.size()) {
                teamLabels[i].setText((i + 1) + ". " + registeredTeams.get(i).getName());
                teamLabels[i].setOpacity(1.0);
            } else {
                teamLabels[i].setText((i + 1) + ". [Disponible]");
                teamLabels[i].setOpacity(0.5);
            }
        }
    }

    private Player showAddPlayerDialog() throws IOException {
        // Charger le fichier FXML de la boîte de dialogue
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_dialogue_ajout_joueur.fxml"));
        Parent dialogRoot = loader.load();

        // Récupérer le contrôleur de la boîte de dialogue
        PlayerDialogController controller = loader.getController();

        // Créer une nouvelle scène
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un joueur");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(dialogRoot));

        // Configurer le contrôleur
        controller.setDialogStage(dialogStage);

        // Afficher la boîte de dialogue et attendre qu'elle soit fermée
        dialogStage.showAndWait();

        // Vérifier si l'utilisateur a cliqué sur le bouton Ajouter
        if (controller.isConfirmClicked()) {
            return controller.getPlayer();
        } else {
            return null;
        }
    }

    private Player showEditPlayerDialog(Player player) throws IOException {
        // Charger le fichier FXML de la boîte de dialogue
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface_dialogue_ajout_joueur.fxml"));
        Parent dialogRoot = loader.load();

        // Récupérer le contrôleur de la boîte de dialogue
        PlayerDialogController controller = loader.getController();

        // Créer une nouvelle scène
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modifier un joueur");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(dialogRoot));

        // Configurer le contrôleur
        controller.setDialogStage(dialogStage);
        controller.setPlayer(player);

        // Afficher la boîte de dialogue et attendre qu'elle soit fermée
        dialogStage.showAndWait();

        // Vérifier si l'utilisateur a cliqué sur le bouton Modifier
        if (controller.isConfirmClicked()) {
            return controller.getPlayer();
        } else {
            return null;
        }
    }

    private void updatePlayerCount() {
        if (playerCountLabel != null) {
            playerCountLabel.setText("Joueurs: " + players.size() + "/" + REQUIRED_PLAYERS);
        }
    }

    private void resetForm() {
        teamNameField.clear();
        managerField.clear();
        emailField.clear();
        phoneField.clear();
        players.clear();
        updatePlayerCount();
    }

    private boolean isValidEmail(String email) {
        // Validation basique d'email
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private boolean isValidPhone(String phone) {
        // Validation basique de numéro de téléphone français
        return phone.matches("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classes internes pour les modèles de données

    public static class Team {
        private String name;
        private String manager;
        private String email;
        private String phone;
        private List<Player> players;

        public Team(String name, String manager, String email, String phone, List<Player> players) {
            this.name = name;
            this.manager = manager;
            this.email = email;
            this.phone = phone;
            this.players = players;
        }

        public String getName() {
            return name;
        }

        public String getManager() {
            return manager;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public List<Player> getPlayers() {
            return players;
        }
    }

    public static class Player {
        private String firstName;
        private String lastName;
        private String email;

        public Player(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return lastName + " " + firstName;
        }
    }

    // Exceptions personnalisées

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class DuplicateTeamException extends Exception {
        public DuplicateTeamException(String message) {
            super(message);
        }
    }

    public static class MaxTeamsReachedException extends Exception {
        public MaxTeamsReachedException(String message) {
            super(message);
        }
    }
}