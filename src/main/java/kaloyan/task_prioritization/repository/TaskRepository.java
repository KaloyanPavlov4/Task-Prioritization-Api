package kaloyan.task_prioritization.repository;

import kaloyan.task_prioritization.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
