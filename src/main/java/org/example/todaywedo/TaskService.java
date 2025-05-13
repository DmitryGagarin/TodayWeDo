package org.example.todaywedo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskService {
    TaskRepository tr = new TaskRepository(JPAUtil.getEmf());

    public void createTask(
        String title,
        String description,
        LocalDate date
    ) {
        TaskEntity task = new TaskEntity();
        task.setTitle(title);
        task.setDescription(description);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = date.format(formatter);
        task.setDate(formattedDate);
        tr.save(task);
    }
}
