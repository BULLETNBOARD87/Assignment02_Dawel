module com.mycompany.assignment02_francesco {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.mycompany.assignment02_francesco to javafx.fxml;
    exports com.mycompany.assignment02_francesco;
}
