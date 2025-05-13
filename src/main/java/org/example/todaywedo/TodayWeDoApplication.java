package org.example.todaywedo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodayWeDoApplication extends Application {

    private static TaskService taskService;
    private static List<TaskEntity> todayTasks;

    public static void setTaskService(TaskService service) {
        taskService = service;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(10);
        root.setStyle(Constants.BACKGROUND + "#FF7582;");

        ScrollPane dateRow = generateDateRow();

        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.CENTER);
        Label mainLabel = new Label("Main Content Here");
        contentBox.getChildren().add(mainLabel);

        VBox timeBox = generateTimeColumn();
        contentBox.getChildren().add(timeBox);

        Button addTaskButton = new Button("Add");
        addTaskButton.setStyle(Constants.BACKGROUND + " #4CAF50; " + Constants.COLOR + " white;");
        addTaskButton.setOnAction((event) -> openAddTaskWindow());

        AnchorPane floatingPane = new AnchorPane(addTaskButton);
        AnchorPane.setRightAnchor(addTaskButton, 20.0);
        AnchorPane.setBottomAnchor(addTaskButton, 20.0);

        StackPane mainArea = new StackPane(contentBox, floatingPane);

        root.getChildren().addAll(dateRow, mainArea);

        Scene scene = new Scene(root, 1000, Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setTitle("TodayWeDo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void openAddTaskWindow() {
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

        ComboBox<String> timeSelector = new ComboBox<>();
        for (int hour = 9; hour <= 22; hour++) {
            String time = String.format("%02d:00", hour);
            timeSelector.getItems().add(time);
        }
        timeSelector.setValue("09:00");

        Button addButton = new Button("Add");
        addButton.setStyle(
            Constants.BACKGROUND + " #4CAF50; " + Constants.COLOR + " white;"
        );

        addVBox.getChildren().addAll(
            title,
            titleOfTask,
            descriptionOfTask,
            dateOfTask,
            timeSelector,
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
                    dateOfTask.getValue(),
                    timeSelector.getValue()
                );
                secondStage.close();
            }
        );
    }

    private static VBox generateTimeColumn() {
        VBox timeBox = new VBox(10);
        timeBox.setPadding(new Insets(30, 10, 0, 10));
        for (int i = 9; i <= 22; i++) {
            Label timeLabel = new Label(i + ":00");
            timeLabel.setPadding(new Insets(10));
            timeLabel.setStyle(
                Constants.BORDER_COLOR + " #333;" +
                Constants.BORDER_WIDTH + " 1;" +
                Constants.BACKGROUND + " #FEABAB;" +
                Constants.COLOR + " black;" +
                Constants.FONT_SIZE + "14px;"
            );
            timeLabel.setMinWidth(100);
            timeBox.getChildren().add(timeLabel);
        }
        return timeBox;
    }

    private static ScrollPane generateDateRow() {
        HBox dateBox = new HBox(10);
        dateBox.setMinHeight(80);
        dateBox.setStyle(
            Constants.BACKGROUND + "#FFDEE9;"
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < 120; i++) {
            LocalDate date = currentDate.plusDays(i);
            Button dateButton = new Button(date.format(formatter));
            dateButton.setId(date.format(formatter));  // Set the button ID with the date
            dateButton.setStyle(
                "-fx-border-color: black; -fx-padding: 8px; -fx-background-color: #FFDEE9;"
            );
            dateBox.getChildren().add(dateButton);
            dateButton.setOnAction((e) -> {
                Button sourceButton = (Button) e.getSource();
                String dateString = sourceButton.getId();

                // Append the current year to the dateString
                int currentYear = LocalDate.now().getYear();
                String dateStringWithYear = dateString + "." + currentYear;

                // Use a custom DateTimeFormatter to parse the date from the button ID (dd.MM.yyyy)
                DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate parsedDate = LocalDate.parse(dateStringWithYear, shortFormatter);  // Parse the date string to LocalDate

                // Call your service method with the parsed date
                todayTasks = taskService.getByDay(parsedDate);
            });
        }

        ScrollPane scrollPane = new ScrollPane(dateBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefHeight(60);

        return scrollPane;
    }

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        setTaskService(taskService);
        launch();
    }
}
