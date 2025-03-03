package com.assignment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Assignment extends Application {

    private static final String IMAGE_FOLDER = "file:C:/Users/T470/Desktop/Assignment/images/";
    private static final int THUMBNAIL_SIZE = 100;
    private static final int FULL_IMAGE_SIZE = 400;

    private List<String> imagePaths = new ArrayList<>();
    private int currentIndex = 0;
    private Stage mainStage;

    @Override
    public void start(Stage stage) {
        this.mainStage = stage;
        loadImages();
        showThumbnailView();
        stage.setResizable(false);

    }

    private void loadImages() {
        for (int i = 1; i <= 15; i++) {
            String imagePath = IMAGE_FOLDER + i + ".jpg";
            if (new File(imagePath.replace("file:", "")).exists()) {
                imagePaths.add(imagePath);
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        }
    }

    private void showThumbnailView() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane"); // Apply CSS

        int col = 0, row = 0;
        for (int i = 0; i < imagePaths.size(); i++) {
            ImageView thumbnail = createThumbnail(imagePaths.get(i), i);
            grid.add(thumbnail, col, row);
            col++;
            if (col == 5) { // 4 images per row
                col = 0;
                row++;
            }
        }
        Label text= new Label("Welcome to my Gallery");
        text.setPrefSize(300, 100);


        text.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        text.setTextFill(Color.DARKBLUE);
        text.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-border-radius: 10px;");


        Pane dark=new Pane();
        dark.setPrefSize(200,80);

        dark.setStyle("-fx-background-color: #3a8ea6;");
        VBox layout = new VBox(text,grid,dark);
        layout.getStyleClass().add("root");

        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        mainStage.setScene(scene);
        mainStage.setTitle("Image Gallery");
        mainStage.show();
    }

    private ImageView createThumbnail(String imagePath, int index) {
        Image image = new Image(imagePath, THUMBNAIL_SIZE, THUMBNAIL_SIZE, true, true);
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("thumbnail");

        imageView.setOnMouseClicked(event -> showFullImage(index));
        return imageView;
    }

    private void showFullImage(int index) {
        currentIndex = index;
        ImageView fullImageView = new ImageView(new Image(imagePaths.get(currentIndex), FULL_IMAGE_SIZE, FULL_IMAGE_SIZE, true, true));
        fullImageView.getStyleClass().add("full-image");

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> navigateImage(-1));

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> navigateImage(1));

        Button backButton = new Button("Back to Gallery");
        backButton.setOnAction(e -> showThumbnailView());

        HBox navigation = new HBox(10, prevButton, nextButton, backButton);
        navigation.getStyleClass().add("navigation");

        BorderPane layout = new BorderPane();
        layout.setCenter(fullImageView);
        layout.setBottom(navigation);
        layout.getStyleClass().add("full-view");

        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());


        mainStage.setScene(scene);
    }

    private void navigateImage(int direction) {
        currentIndex += direction;
        if (currentIndex < 0) currentIndex = imagePaths.size() - 1;
        if (currentIndex >= imagePaths.size()) currentIndex = 0;
        showFullImage(currentIndex);
    }

    public static void main(String[] args) {
        launch();
    }
}