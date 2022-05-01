module controle.estoque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens controle.estoque to javafx.fxml;
    exports controle.estoque;
}
