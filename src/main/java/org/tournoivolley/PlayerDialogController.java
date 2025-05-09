package org.tournoivolley;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.tournoivolley.InscriptionController.Player;

public class PlayerDialogController {
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField emailField;
    
    private Stage dialogStage;
    private Player player;
    private boolean confirmClicked = false;
    
    /**
     * Initialise le contrôleur.
     */
    @FXML
    private void initialize() {
        // Initialisation...
    }
    
    /**
     * Configure la fenêtre de dialogue
     * @param dialogStage la fenêtre de dialogue
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Retourne true si l'utilisateur a cliqué sur le bouton Ajouter
     * @return confirmClicked
     */
    public boolean isConfirmClicked() {
        return confirmClicked;
    }
    
    /**
     * Retourne le joueur créé
     * @return player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Gère le clic sur le bouton Ajouter
     */
    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            player = new Player(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                emailField.getText().trim()
            );
            
            confirmClicked = true;
            dialogStage.close();
        }
    }
    
    /**
     * Gère le clic sur le bouton Annuler
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    /**
     * Valide les données saisies par l'utilisateur
     * @return true si l'entrée est valide
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (firstNameField.getText() == null || firstNameField.getText().trim().isEmpty()) {
            errorMessage += "Le prénom est obligatoire!\n";
        }
        
        if (lastNameField.getText() == null || lastNameField.getText().trim().isEmpty()) {
            errorMessage += "Le nom est obligatoire!\n";
        }
        
        if (emailField.getText() == null || emailField.getText().trim().isEmpty()) {
            errorMessage += "L'email est obligatoire!\n";
        } else if (!isValidEmail(emailField.getText())) {
            errorMessage += "L'adresse email n'est pas valide!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Afficher un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Données invalides");
            alert.setHeaderText("Veuillez corriger les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            
            return false;
        }
    }
    
    private boolean isValidEmail(String email) {
        // Validation basique d'email
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}