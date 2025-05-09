module org.tournoivolley {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.tournoivolley to javafx.fxml;
    exports org.tournoivolley;
}