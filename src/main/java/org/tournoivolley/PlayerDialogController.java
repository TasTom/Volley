package org.tournoivolley;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

/**
 * Contrôleur pour la boîte de dialogue d'ajout/modification de joueur.
 * Permet de saisir ou modifier les informations d'un joueur.
 */
public class PlayerDialogController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private java.util.List<InscriptionController.Player> existingPlayers;

    private Stage dialogStage;
    private InscriptionController.Player player;
    private boolean confirmClicked = false;
    private boolean editMode = false;

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
     * Définit le joueur à modifier
     * @param player le joueur à modifier
     */
    public void setPlayer(InscriptionController.Player player) {
        this.player = player;
        this.editMode = true;

        // Pré-remplir les champs avec les informations du joueur
        firstNameField.setText(player.getFirstName());
        lastNameField.setText(player.getLastName());
        emailField.setText(player.getEmail());
    }

    public void setExistingPlayers(List<InscriptionController.Player> players) {
        this.existingPlayers = players;
    }


    /**
     * Retourne le joueur créé ou modifié
     * @return player
     */
    public InscriptionController.Player getPlayer() {
        return player;
    }




    /**
     * Retourne true si l'utilisateur a cliqué sur le bouton Ajouter/Modifier
     * @return confirmClicked
     */
    public boolean isConfirmClicked() {
        return confirmClicked;
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

        // Vérifier si le joueur existe déjà
        if (existingPlayers != null && !existingPlayers.isEmpty()) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();

            for (InscriptionController.Player existingPlayer : existingPlayers) {
                // Si on est en mode édition, ignorer le joueur actuel dans la comparaison
                if (editMode && player != null &&
                        player.getFirstName().equals(existingPlayer.getFirstName()) &&
                        player.getLastName().equals(existingPlayer.getLastName()) &&
                        player.getEmail().equals(existingPlayer.getEmail())) {
                    continue;
                }

                // Vérifier si un joueur avec le même email existe déjà
                if (email.equals(existingPlayer.getEmail())) {
                    errorMessage += "Un joueur avec cet email existe déjà!\n";
                    break;
                }

                // Vérifier si un joueur avec le même nom et prénom existe déjà
                if (firstName.equals(existingPlayer.getFirstName()) &&
                        lastName.equals(existingPlayer.getLastName())) {
                    errorMessage += "Un joueur avec ce nom et prénom existe déjà!\n";
                    break;
                }
            }
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



    /**
     * Gère le clic sur le bouton Ajouter/Modifier
     */
    @FXML
    private void handleAdd() {
        if (isInputValid()) {
            if (editMode && player != null) {
                // Mise à jour des informations du joueur existant
                // Remarque: comme les setters ne sont pas disponibles dans la classe Player fournie,
                // nous devrons créer une nouvelle instance
                player = new InscriptionController.Player(
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        emailField.getText().trim()
                );
            } else {
                // Création d'un nouveau joueur
                player = new InscriptionController.Player(
                        firstNameField.getText().trim(),
                        lastNameField.getText().trim(),
                        emailField.getText().trim()
                );
            }

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
     * Valide le format de l'adresse email
     * @param email l'adresse email à valider
     * @return true si l'email est valide
     */
    private boolean isValidEmail(String email) {
        // Validation basique d'email
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}