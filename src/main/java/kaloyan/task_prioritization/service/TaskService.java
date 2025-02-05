package kaloyan.task_prioritization.service;

import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.utils.Priority;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<Task> getTasks(Boolean paramBoolean, Priority paramPriority, String paramString, int paramInt1, int paramInt2);

    Task getTaskById(long paramLong);

    Task createTask(Task paramTask);

    Task updateTask(long paramLong, Task paramTask);

    void deleteTask(long paramLong);
}
