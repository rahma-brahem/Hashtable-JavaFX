package tn_usousse_eniso_stage.presentation.view;

import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tn_usousse_eniso_stage.model.Node;
import tn_usousse_eniso_stage.presentation.controller.HashtableController;

import java.util.Optional;

public class HashTableDrawComponent {

    private HashtableController hashtableController;
    private boolean firstTime = false;
    private int index;

    public HashTableDrawComponent(HashtableController hashtableController) {
        this.hashtableController = hashtableController;

    }

    //public Pane pane = new Pane();

    public void setIndex(int index) {
        this.index = index;
    }

    public Pane drawTable() {
        int tableSize = hashtableController.getService().getTable().getNodes().length;
        int x = 50; // Starting x-coordinate of the table
        int y = 50; // Starting y-coordinate of the table
        int width = 100; // Width of each cell
        int height = 50; // Height of each cell
        int size = tableSize;
        boolean isColored = true;
        // size of the table
        Pane pane = new Pane();
        // Draw table header
        Rectangle headerRect = new Rectangle(x, y, width, height);
        headerRect.setFill(null);
        headerRect.setStroke(Color.BLACK);
        Text headerText = new Text(x + 5, y + height / 2, "HashTable");
        headerText.setFont(new Font("Arial", 12));

        pane.getChildren().addAll(headerRect, headerText);

        // Draw table rows
        Node[] nodes = hashtableController.getModel().getNodes();

        for (int i = 0; i < size; i++) {
            Rectangle rowRect = new Rectangle(x, y + (i * height) + height, width, height);
            Node n = nodes[i];
            rowRect.setFill(null);
            rowRect.setStroke(Color.BLACK);
            pane.getChildren().add(rowRect);

            int xPos = x;
            int j = 0;
            while (n != null) {
                j += 1;
                Rectangle nodeRect = drawNode(n, i, pane, xPos, y, width, height);
                if (index == i) {
                    addAnimation(pane, i);
                    animateRec(rowRect, i);
                    nodeAnimation(n, nodeRect, j);

                }
                /*if(n.isLast()) {
                    //addAnimation(pane, i);
                    //nodeAnimation(n,i,pane, x,  y, width,height);
                    isColored=false;


                }*/


                if (n.getNext() == null) {
                    drawNull(n, i, pane, xPos + width + 30, y, width, height);
                }
                n = n.getNext();
                xPos = xPos + width + 30;
            }
        }
        firstTime = true;

        return pane;
    }

    private void nodeAnimation(Node n, Rectangle nodeRect, int i) {
        Timeline timeline = new Timeline(

                new KeyFrame(Duration.seconds((i + 1)), event -> {

                    FillTransition fillO = new FillTransition();
                    nodeRect.setFill(Color.ORANGE);
                    fillO.setFromValue(Color.ORANGE);
                    fillO.setToValue(Color.WHITE);
                    fillO.setDuration(Duration.millis(1000));
                    fillO.setShape(nodeRect);
                    fillO.play();

                })


        );
        timeline.play();

    }

    public void addAnimation(Pane pane, int i) {


        for (int z = 0; z <= i; z++) {

            Text cirText = new Text(25, 125, Integer.toString(z));
            cirText.setFont(new Font("Arial", 12));

            TranslateTransition translate = new TranslateTransition();
            translate.setByY(z * 50);
            translate.setDuration(Duration.millis(500));
            translate.setCycleCount((1));
            translate.setAutoReverse(false);
            translate.setNode(cirText);
            pane.getChildren().add(cirText);
            translate.playFromStart();


        }


    }

    private void animateRec(Rectangle rectangle, int i) {
        Timeline tm = new Timeline(

                new KeyFrame(Duration.millis((i * 100 + 1)), event -> {
                    FillTransition fill = new FillTransition();
                    fill.setAutoReverse(false);
                    fill.setCycleCount(1);
                    fill.setDuration(Duration.millis(1000));
                    fill.setFromValue(Color.WHITE);
                    fill.setToValue(Color.GREEN);
                    fill.setShape(rectangle);
                    fill.playFromStart();
                })


        );
        tm.play();
    }

    private Rectangle drawNode(Node node, int i, Pane pane, int x, int y, int width, int height) {
        int lineY = 125 + 50 * i;
        Line line = new javafx.scene.shape.Line(x + width, lineY, x + width + 50, lineY);
        pane.getChildren().add(line);

        int rectY = lineY + width;

                    Rectangle rect = new Rectangle(x + width + 50, rectY - width - 30, width - 20, 40);
                    rect.setFill(Color.WHITE);
                    rect.setStroke(Color.BLACK);
                    pane.getChildren().add(rect);


                    node.setLast(false);


        rect.setOnMouseClicked(event -> {

            // Show confirmation dialog
            Button remove = new Button("remove");
            remove.setStyle("-fx-border-color: black; -fx-text-fill: black; -fx-border-width: 3px; -fx-font-size: 17px;");
            pane.getChildren().add(remove);
            remove.setLayoutX(x + width + 50);
            remove.setLayoutY(rectY - width - 30);
            remove.setOnAction(e -> {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Remove Node");
                alert.setContentText("Are you sure you want to remove this node?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Remove the node and redraw the table
                    hashtableController.getService().remove(node.getValue());
                    pane.getChildren().clear();
                    pane.getChildren().addAll(drawTableAfterRemove());
                }
            });

        });
        rect.setOnMouseEntered(mouseEvent -> {
            rect.setFill(Color.RED);

        });
        rect.setOnMouseExited(mouseEvent -> {
            rect.setFill(Color.WHITE);

        });

        Text text = new Text(x + width + 75, lineY, node.getValue());
        pane.getChildren().add(text);

        return rect;



    }

    private void drawNull(Node node, int i, Pane pane, int x, int y, int width, int height) {
        int lineY = 125 + 50 * i;
        pane.getChildren().add(new javafx.scene.shape.Line(x + width, lineY, x + width + 50, lineY));

        int lineX = x + width + 50;
        pane.getChildren().add(new javafx.scene.shape.Line(lineX, lineY + 20, lineX, lineY - 20));
        pane.getChildren().add(new javafx.scene.shape.Line(lineX, lineY + 10, lineX + 10, lineY));
        pane.getChildren().add(new javafx.scene.shape.Line(lineX, lineY - 10, lineX + 10, lineY - 20));
    }

    private Pane drawTableAfterRemove() {
        int tableSize = hashtableController.getService().getTable().getNodes().length;
        int x = 50; // Starting x-coordinate of the table
        int y = 50; // Starting y-coordinate of the table
        int width = 100; // Width of each cell
        int height = 50; // Height of each cell
        int size = tableSize;

        // size of the table
        Pane pane = new Pane();
        // Draw table header
        Rectangle headerRect = new Rectangle(x, y, width, height);
        headerRect.setFill(null);
        headerRect.setStroke(Color.BLACK);
        Text headerText = new Text(x + 5, y + height / 2, "HashTable");
        headerText.setFont(new Font("Arial", 12));

        pane.getChildren().addAll(headerRect, headerText);

        // Draw table rows
        Node[] nodes = hashtableController.getModel().getNodes();

        for (int i = 0; i < size; i++) {
            Rectangle rowRect = new Rectangle(x, y + (i * height) + height, width, height);
            Node n = nodes[i];
            rowRect.setFill(null);
            rowRect.setStroke(Color.BLACK);
            pane.getChildren().add(rowRect);
            int xPos = x;
            while (n != null) {

                Rectangle nodeRect = drawNode(n, i, pane, xPos, y, width, height);

                if (n.getNext() == null) {
                    drawNull(n, i, pane, xPos + width + 30, y, width, height);
                }
                n = n.getNext();
                xPos = xPos + width + 30;
            }
        }


        return pane;
    }


}