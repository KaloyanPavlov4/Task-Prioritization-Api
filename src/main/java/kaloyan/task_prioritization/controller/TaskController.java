package kaloyan.task_prioritization.controller;

import jakarta.validation.Valid;
import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.model.dto.TaskDTO;
import kaloyan.task_prioritization.service.TaskService;
import kaloyan.task_prioritization.utils.Priority;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam(value = "filter", required = false) String filter,
                                               @RequestParam(value = "value", required = false) String value,
                                               @RequestParam(value = "sort", defaultValue = "priority") String sortBy) {
        Boolean isCompleted = null;
        Priority priority = null;
        if ("isCompleted".equalsIgnoreCase(filter) && value != null) {
            isCompleted = Boolean.parseBoolean(value);
        } else if ("priority".equalsIgnoreCase(filter) && value != null) {
            try {
                priority = Priority.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        Page<Task> tasks = this.taskService.getTasks(isCompleted, priority, sortBy, page, size);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping({"/{taskId"})
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(this.taskService.getTaskById(taskId.longValue()));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid TaskDTO request) {
        Task createdTask = this.taskService.createTask(request.toTask());
        return ResponseEntity.status((HttpStatusCode)HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping({"/{taskId}"})
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskDTO request) {
        Task updatedTask = this.taskService.updateTask(taskId, request.toTask());
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping({"/{taskId}"})
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        this.taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}

