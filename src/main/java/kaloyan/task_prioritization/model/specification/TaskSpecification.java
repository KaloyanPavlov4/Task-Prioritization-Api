package kaloyan.task_prioritization.model.specification;

import kaloyan.task_prioritization.model.Task;
import kaloyan.task_prioritization.utils.Priority;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> filterTasksByCompletion(boolean isCompleted) {
        return (root, query, builder) -> builder.and(builder.equal(root.get("isCompleted"), isCompleted));
    }

    public static Specification<Task> filterTasksByPriority(Priority priority) {
        return (root, query, builder) -> builder.and(builder.equal(root.get("priority"), priority));
    }
}
