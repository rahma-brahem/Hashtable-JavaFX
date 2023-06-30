package tn_usousse_eniso_stage.presentation.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn_usousse_eniso_stage.model.Table;
import tn_usousse_eniso_stage.presentation.controller.HashtableController;
import tn_usousse_eniso_stage.service.Service;

import java.util.Optional;

public class Presentation {
    int size;

    HashtableController hashtableController = new HashtableController(new Service(new Table(0)));
    HashTableDrawComponent drawComponent = new HashTableDrawComponent(hashtableController);

    public void showPresentation(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 1000);
        //Image icon=new Image(getClass().getResourceAsStream("/image/icons8-wattpad-50.png"));
        stage.getIcons().add(new Image("https://img.icons8.com/?size=512&id=VSoyUZwGrQ7O&format=png"));
        stage.setTitle("Hashtable FX by Rahma");

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        MenuItem fileMenu1 = new MenuItem("New");
        MenuItem fileMenu2 = new MenuItem("Add");
        MenuItem fileMenu3 = new MenuItem("Exit");

        Menu helpMenu = new Menu("Help");
        MenuItem helpMenu1 = new MenuItem("About");
        helpMenu1.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.setContentText("Welcome to Hashtable app with JavaFX");
            alert.showAndWait();
        });

        fileMenu1.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("table size");
            dialog.setTitle("New Hashtable");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter table size");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                System.out.println("size: " + result.get());
                try {
                    size = Integer.parseInt(result.get());
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("INVALID SIZE. TRY TO ENTER A NUMBER");
                    alert.showAndWait();
                    return;
                }

                hashtableController.getService().setTable(new Table(size));
                root.setCenter(drawComponent.drawTable());
            }
        });

        fileMenu2.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("add name");
            dialog.setTitle("Add Name to Hashtable");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter a name");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                System.out.println("name: " + result.get());
                drawComponent.setIndex(hashtableController.getService().hash(result.get()));

                hashtableController.getService().add(result.get());
                root.setCenter(drawComponent.drawTable());
            }
        });
        fileMenu3.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Exit Application");
            alert.setContentText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.close();
            }
        });

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        fileMenu.getItems().addAll(fileMenu1, fileMenu2, fileMenu3);
        helpMenu.getItems().addAll(helpMenu1);
        root.setTop(menuBar);
        stage.setScene(scene);
        stage.show();
    }
}
