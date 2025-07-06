module com.example.demo {
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires javafx.media;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.View;
    opens com.example.demo.View to javafx.fxml;
    exports com.example.demo.Controller;
    opens com.example.demo.Controller to javafx.fxml;
}