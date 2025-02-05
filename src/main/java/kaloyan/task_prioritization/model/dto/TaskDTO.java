package kaloyan.task_prioritization.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kaloyan.task_prioritization.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "The title is required")
    @Size(max = 100, message = "The length of the title should not be more than 100 characters")
    private String title;

    @NotBlank(message = "The description is required")
    @Size(max = 1000, message = "The length of the description should not be more than 1000 characters")
    private String description;

    @FutureOrPresent(message = "Due date should not be in the past")
    @JsonProperty("dueDate")
    private LocalDate dueDate;

    @JsonProperty("isCompleted")
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
