package tn_usousse_eniso_stage;

import javafx.application.Application;
import javafx.stage.Stage;
import tn_usousse_eniso_stage.presentation.view.Presentation;

import java.io.IOException;

public class HelloApplication extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //StackPane root=new StackPane();
        Presentation presentation = new Presentation();
        presentation.showPresentation(stage);

    }
}