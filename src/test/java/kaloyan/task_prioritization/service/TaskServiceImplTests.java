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
        assertEquals("Task1", task.getTitle());
    }

    @Test
    public void getAllTasks_ShouldReturnCorrectAmount() {
        assertEquals(3, taskService.getTasks(null, null, "priority", 0, 50).getTotalElements());
    }

    @Test
    public void getTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1000L));
    }

    @Test
    public void getTasksFilterByPriority_ShouldReturnCorrectTasks() {
        Task task = taskService.getTasks(null, Priority.MEDIUM, "priority", 0, 5).getContent().get(0);
        assertEquals(Priority.MEDIUM, task.getPriority());
        assertEquals("Task2", task.getTitle());
    }

    @Test
    public void getTasksFilterByCompletion_ShouldReturnCorrectTasks() {
        Task task = taskService.getTasks(true, null, "priority", 0, 5).getContent().get(0);
        assertTrue(task.isCompleted());
        assertEquals("Task2", task.getTitle());
    }

    @Test
    public void getTasksSortByPriority_ShouldReturnHighPriorityTasksFirst() {
        Task task = taskService.getTasks(null, null, "priority", 0, 5).getContent().get(0);
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals("Task1", task.getTitle());
    }

    @Test
    public void getTasksSortByDueDate_ShouldReturnEarliestFirst() {
        Task task = taskService.getTasks(null, null, "dueDate", 0, 5).getContent().get(0);
        assertEquals("Task3", task.getTitle());
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
        assertEquals(2, taskService.getTasks(null, null, "priority", 0, 50).getTotalElements());
    }

}
