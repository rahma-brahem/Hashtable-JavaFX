module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens tn_usousse_eniso_stage to javafx.fxml;
    exports tn_usousse_eniso_stage;
}