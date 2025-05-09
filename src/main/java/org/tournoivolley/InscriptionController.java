package org.tournoivolley;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

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
    
    // Liste pour stocker les joueurs de l'équipe en cours
    private List<Player> players = new ArrayList<>();
    
    // Liste pour stocker toutes les équipes inscrites
    private static List<Team> registeredTeams = new ArrayList<>();
    
    // Constantes
    private static final int MAX_TEAMS = 8;
    private static final int REQUIRED_PLAYERS = 6;
    
    @FXML
    private void initialize() {
        // Initialisation du contrôleur
    }
    
    @FXML
    private void handleAddPlayer() {
        try {
            // Vérifier si l'équipe a déjà 6 joueurs
            if (players.size() >= REQUIRED_PLAYERS) {
                showAlert(AlertType.WARNING, "Limite atteinte", 
                          "Une équipe ne peut pas avoir plus de 6 joueurs.");
                return;
            }
            
            // Ouvrir une boîte de dialogue pour ajouter un joueur
            showAddPlayerDialog();
            
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Erreur", 
                     "Une erreur est survenue lors de l'ajout d'un joueur: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSubmit() {
        try {
            // Validation des champs
            if (teamNameField.getText().trim().isEmpty()) {
                throw new ValidationException("Le nom de l'équipe est obligatoire.");
            }
            
            if (managerField.getText().trim().isEmpty()) {
                throw new ValidationException("Le nom du responsable est obligatoire.");
            }
            
            if (emailField.getText().trim().isEmpty() || !isValidEmail(emailField.getText())) {
                throw new ValidationException("Veuillez fournir une adresse email valide.");
            }
            
            if (phoneField.getText().trim().isEmpty() || !isValidPhone(phoneField.getText())) {
                throw new ValidationException("Veuillez fournir un numéro de téléphone valide.");
            }
            
            // Vérifier le nombre de joueurs
            if (players.size() < REQUIRED_PLAYERS) {
                throw new ValidationException("Vous devez ajouter exactement 6 joueurs.");
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
                throw new MaxTeamsReachedException("Le nombre maximum d'équipes (8) est atteint.");
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
            
            // Afficher une confirmation
            showAlert(AlertType.INFORMATION, "Inscription réussie", 
                     "L'équipe '" + newTeam.getName() + "' a été inscrite avec succès!");
            
            // Réinitialiser le formulaire
            resetForm();
            
        } catch (ValidationException | DuplicateTeamException | MaxTeamsReachedException e) {
            showAlert(AlertType.ERROR, "Erreur d'inscription", e.getMessage());
        }
    }
    
    private void showAddPlayerDialog() throws IOException {
        // Création d'une boîte de dialogue pour ajouter un joueur
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ajouter un joueur");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        
        // Ici, vous chargerez normalement un FXML pour le dialogue
        // Pour simplifier, nous créons un dialogue basique
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPrefSize(400, 300);
        dialogVBox.setStyle("-fx-padding: 20;");
        
        Scene dialogScene = new Scene(dialogVBox);
        dialogStage.setScene(dialogScene);
        
        // Afficher le dialogue
        dialogStage.showAndWait();
    }
    
    private void resetForm() {
        teamNameField.clear();
        managerField.clear();
        emailField.clear();
        phoneField.clear();
        players.clear();
        // Mise à jour du label de joueurs
        // playerCountLabel.setText("Joueurs: 0/6");
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
        
        // Autres getters et setters
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
        
        // Getters et setters
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