package org.example.todaywedo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TodayWeDoApplication extends Application {

    private static TaskService taskService;

    public static void setTaskService(TaskService service) {
        taskService = service;
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setPadding(new Insets(20));

        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        Label mainLabel = new Label("Main Content Here");
        contentBox.getChildren().add(mainLabel);

        Button addTaskButton = new Button("Add");
        addTaskButton.setStyle(
            Constants.BACKGROUND + " #4CAF50; " + Constants.COLOR + " white;"
        );

        // ADD BUTTON
        AnchorPane floatingPane = new AnchorPane(addTaskButton);
        AnchorPane.setRightAnchor(addTaskButton, 20.0);
        AnchorPane.setBottomAnchor(addTaskButton, 20.0);

        root.getChildren().addAll(contentBox, floatingPane, addTaskButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();

        // CREATE TASK WINDOW
        addTaskButton.setOnAction((event) -> {
            StackPane stackPane = new StackPane();

            Stage secondStage = new Stage();
            Scene addTaskScene = new Scene(stackPane, 500, 300);

            VBox addVBox = new VBox();
            addVBox.setAlignment(Pos.CENTER);
            addVBox.setSpacing(20);
            addVBox.setPadding(new Insets(40, 20, 0, 20));

            Label title = new Label("Add");

            TextField titleOfTask = new TextField();
            titleOfTask.setPromptText("Task Title");

            TextField descriptionOfTask = new TextField();
            descriptionOfTask.setPromptText("Task Description");

            DatePicker dateOfTask = new DatePicker();
            dateOfTask.setPromptText("Task Date");

            Button addButton = new Button("Add");
            addButton.setStyle(
                Constants.BACKGROUND + " #4CAF50; " + Constants.COLOR + " white;"
            );

            addVBox.getChildren().addAll(
                title,
                titleOfTask,
                descriptionOfTask,
                dateOfTask,
                addButton
            );

            secondStage.setScene(addTaskScene);
            secondStage.setTitle("Add Task");
            secondStage.show();
            stackPane.getChildren().add(addVBox);

            addButton.setOnAction((e) -> {
                    taskService.createTask(
                        titleOfTask.getText(),
                        descriptionOfTask.getText(),
                        dateOfTask.getValue()
                    );
                    secondStage.close();
                }
            );
        });
    }

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        setTaskService(taskService);
        launch();
    }
}
