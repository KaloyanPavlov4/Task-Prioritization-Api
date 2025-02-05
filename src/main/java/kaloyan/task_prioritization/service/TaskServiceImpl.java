package kaloyan.task_prioritization.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import kaloyan.task_prioritization.exception.TaskNotFoundException;
import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.model.specification.TaskSpecification;
import kaloyan.task_prioritization.repository.TaskRepository;
import kaloyan.task_prioritization.utils.Priority;
import kaloyan.task_prioritization.utils.SentimentAnalysis;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final String TASK_NOT_FOUND_EXCEPTION_MESSAGE = "Task with id %d was not found";

    private TaskRepository taskRepository;

    public Page<Task> getTasks(Boolean isCompleted, Priority priority, String sortBy, int page, int size) {
        Sort sort = getSorting(sortBy);
        Specification<Task> spec = null;
        if (isCompleted != null) {
            spec = TaskSpecification.filterTasksByCompletion(isCompleted);
        } else if (priority != null) {
            spec = TaskSpecification.filterTasksByPriority(priority);
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return this.taskRepository.findAll(spec, pageRequest);
    }

    public Task getTaskById(long id) {
        return (Task)this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %d was not found", new Object[] { Long.valueOf(id) })));
    }

    public Task createTask(Task task) {
        task.setPriority(calculatePriority(task));
        task.setSentiment(SentimentAnalysis.getSentiment(task.getTitle() + " " + task.getTitle()));
        return (Task)this.taskRepository.save(task);
    }

    public Task updateTask(long id, Task task) {
        Task taskToUpdate = (Task)this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %d was not found", new Object[] { Long.valueOf(id) })));
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setCompleted(task.isCompleted());
        taskToUpdate.setCritical(task.isCritical());
        taskToUpdate.setPriority(calculatePriority(task));
        taskToUpdate.setSentiment(SentimentAnalysis.getSentiment(task.getTitle() + " " + task.getTitle()));
        return (Task)this.taskRepository.save(taskToUpdate);
    }

    public void deleteTask(long id) {
        this.taskRepository.deleteById(id);
    }

    private Sort getSorting(String sortBy) {
        if (sortBy.equalsIgnoreCase("dueDate"))
            return Sort.by(new Sort.Order[] { Sort.Order.asc("dueDate") });
        return Sort.by(new Sort.Order[] { Sort.Order.asc("priority") });
    }

    private Priority calculatePriority(Task task) {
        if (task.isCompleted())
            return Priority.LOW;
        if (task.isCritical())
            return Priority.HIGH;
        if (task.getDueDate() != null) {
            LocalDate today = LocalDate.now();
            long daysUntilDue = ChronoUnit.DAYS.between(today, task.getDueDate());
            if (daysUntilDue <= 1L)
                return Priority.HIGH;
            if (daysUntilDue <= 3L)
                return Priority.MEDIUM;
        }
        return Priority.LOW;
    }
}
