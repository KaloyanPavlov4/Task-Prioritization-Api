package kaloyan.task_prioritization.service;

import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.repository.TaskRepository;
import kaloyan.task_prioritization.utils.Priority;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public Task createTask(Task task) {
        calculatePriority(task);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(long id, Task task) {
        Task taskToUpdate = taskRepository.findById(id).orElseThrow();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setCompleted(task.isCompleted());
        taskToUpdate.setCritical(task.isCritical());
        calculatePriority(task);
        return taskRepository.save(taskToUpdate);
    }

    @Override
    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    private void calculatePriority(Task task) {
        if (task.isCompleted()) {
            task.setPriority(Priority.LOW);
            return;
        }

        if (task.isCritical()) {
            task.setPriority(Priority.HIGH);
            return;
        }

        LocalDate today = LocalDate.now();
        long daysUntilDue = ChronoUnit.DAYS.between(today, task.getDueDate());

        if (daysUntilDue <= 1) {
            task.setPriority(Priority.HIGH);
        } else if (daysUntilDue <= 3) {
            task.setPriority(Priority.MEDIUM);
        } else {
            task.setPriority(Priority.LOW);
        }
    }
}
