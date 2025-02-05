package kaloyan.task_prioritization.service;

import jakarta.transaction.Transactional;
import kaloyan.task_prioritization.exception.TaskNotFoundException;
import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.utils.Priority;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class TaskServiceImplTests {

    @Autowired
    private TaskServiceImpl taskService;

    private Task mediumPriorityTask;
    private Task criticalTask;

    @BeforeAll
    public void init() {
        LocalDate twoDaysFromToday = LocalDate.now().plusDays(2);
        mediumPriorityTask = Task.builder()
                .title("medium")
                .description("medium")
                .dueDate(twoDaysFromToday)
                .isCompleted(false)
                .isCritical(false)
                .build();

        criticalTask = Task.builder()
                .title("medium")
                .description("medium")
                .dueDate(twoDaysFromToday)
                .isCompleted(false)
                .isCritical(true)
                .build();
    }


    @Test
    public void getTaskById_WhenTaskExists_ShouldReturnTask() {
        Task task = taskService.getTaskById(100);
        assertEquals("Fix Bug", task.getTitle());
    }

    @Test
    public void getAllTasks_ShouldReturnCorrectAmount() {
        assertEquals(3, taskService.getTasks(null, null, "priority").size());
    }

    @Test
    public void getTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1000L));
    }

    @Test
    public void createTask_ShouldSetPriorityAndSave() {
        Task createdTask = taskService.createTask(mediumPriorityTask);
        assertNotNull(createdTask);
        assertEquals(Priority.MEDIUM, createdTask.getPriority());
    }

    @Test
    public void createCriticalTask_ShouldSetHighPriorityAndSave() {
        Task createdTask = taskService.createTask(criticalTask);
        assertNotNull(createdTask);
        assertEquals(Priority.HIGH, createdTask.getPriority());
    }

    @Test
    public void deleteTask_ShouldDelete() {
        taskService.deleteTask(100L);
        assertEquals(2, taskService.getTasks(null, null, "priority").size());
    }

}
