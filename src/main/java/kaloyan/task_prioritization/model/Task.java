package kaloyan.task_prioritization.model;

import jakarta.persistence.*;
import kaloyan.task_prioritization.utils.Priority;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "is_completed", nullable = false)
    @ColumnDefault("false")
    private boolean isCompleted;

    @Transient
    private boolean isCritical;
}
