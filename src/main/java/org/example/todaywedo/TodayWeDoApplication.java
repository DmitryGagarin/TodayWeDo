package org.example.todaywedo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TodayWeDoApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button addTaskButton = new Button("Add");
        root.getChildren().add(addTaskButton);

        addTaskButton.setOnAction((event) -> {
            StackPane stackPane = new StackPane();
            Label title = new Label("Add");
            stackPane.getChildren().add(title);
            Scene addTaskScene = new Scene(stackPane, 500, 300);
            Stage secondStage = new Stage();
            secondStage.setScene(addTaskScene);
            secondStage.setTitle("Add Task");
            secondStage.show();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
