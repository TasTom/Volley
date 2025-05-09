package org.tournoivolley;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TournoiVolleyballApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
        Parent root = loader.load();
        
        // Configurer la scène principale
        Scene scene = new Scene(root);
        
        // Configurer la fenêtre principale
        primaryStage.setTitle("Gestion du Tournoi de Volley-ball");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    /**
     * Point d'entrée principal de l'application
     * @param args arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        launch(args);
    }
}