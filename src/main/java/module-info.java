module org.jhproject.memorysequencegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jhproject.memorysequencegame to javafx.fxml;
    exports org.jhproject.memorysequencegame;
}