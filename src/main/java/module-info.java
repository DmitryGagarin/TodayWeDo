module org.example.todaywedo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;

    opens org.example.todaywedo to javafx.fxml;
    exports org.example.todaywedo;
}