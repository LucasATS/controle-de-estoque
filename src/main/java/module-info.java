module controle.estoque {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens controle.estoque to javafx.fxml;
    exports controle.estoque;
}
