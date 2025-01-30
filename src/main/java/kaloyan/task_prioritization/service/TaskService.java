package kaloyan.task_prioritization.service;

import kaloyan.task_prioritization.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(long id);

    Task createTask(Task task);

    Task updateTask(long id, Task task);

    void deleteTask(long id);
}
