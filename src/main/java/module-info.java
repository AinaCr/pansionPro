module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires javafx.swing;      // <-- à ajouter
    requires java.desktop;
    requires org.apache.pdfbox;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}