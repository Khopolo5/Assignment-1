module com.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.assignment to javafx.fxml;
    exports com.assignment;
}