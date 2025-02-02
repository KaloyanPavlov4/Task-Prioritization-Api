package kaloyan.task_prioritization.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank(message = "The title is required")
    @Size(max = 100, message = "The length of the title should not be more than 100 characters")
    private String title;

    @NotBlank(message = "The description is required")
    @Size(max = 1000, message = "The length of the description should not be more than 1000 characters")
    private String description;

    @FutureOrPresent(message = "Due date should not be in the past")
    private LocalDate dueDate;

    private boolean isCompleted;

    private boolean isCritical;

    public Task toTask() {
        return Task.builder()
                .title(title)
                .description(description)
                .dueDate(dueDate)
                .isCompleted(isCompleted)
                .isCritical(isCritical)
                .build();
    }
}
