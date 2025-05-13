package org.example.todaywedo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    TaskRepository tr = new TaskRepository(JPAUtil.getEmf());

    public void createTask(
        String title,
        String description,
        LocalDate date,
        String time
    ) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setDescription(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = date.format(formatter);
        task.setDate(formattedDate);
        task.setTime(time);
        tr.save(task);
    }

    public List<TaskEntity> getByDay(LocalDate date) {
        List<TaskEntity> tasks = tr.findAll();
        List<TaskEntity> tasksAtDate = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        String formattedDate = date.format(formatter);

        for (var task : tasks) {
            String taskDate = task.getDate().substring(0, 5);
            if (taskDate.equals(formattedDate)) {
                tasksAtDate.add(task);
            }
        }

        return tasksAtDate;
    }

}
