module org.jhproject.memorysequencegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires javafx.base;


    opens org.jhproject.memorysequencegame to javafx.fxml;
    exports org.jhproject.memorysequencegame;
}